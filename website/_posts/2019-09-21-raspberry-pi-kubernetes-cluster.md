---
layout: post
title: Raspberry Pi Kubernetes Cluster
selected: blog
---
<a href="/assets/posts/2019-09-21-raspberry-pi-kubernetes-cluster/thumb.png">
    <img alt="K8 and Pi logo" src="/assets/posts/2019-09-21-raspberry-pi-kubernetes-cluster/thumb.png" class="post-thumb" />
</a>

In this article, I setup:
- A Kubernetes cluster using the new Raspberry Pi 4.
- Kubernetes Dashboard.
- Ingress using NGINX.

Many articles already exist for older Debian distributions and Raspberry Pis,
so hopefully this updated set of steps helps others save time.

## Kubernetes Cluster Setup
In this section is the setup of a basic cluster, which consists of the
following:
- A single control plane node, used to manage the cluster.
- One or more nodes, used to later run our application deployments (pods, containers, etc).

A node is just a traditional bare-metal or virtual host, such as a Raspberry Pi.

Relevant official docs:
- [Concepts](https://kubernetes.io/docs/concepts/#kubernetes-control-plane).
- [Single control-plane cluster with kubeadm](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/)


### Generic Raspberry Pi Setup
Run these steps for both your single control plane node, and node(s):

- [Download the latest Raspbian imag](https://www.raspberrypi.org/downloads/raspbian) and
  install it onto the micro SD card.

  At the time of this article, the latest  Raspbian Buster. I've chosen the lite
  image, as everything will be  remotely and no desktop is required.

- Create `ssh.txt` in `/boot` partition of micro SD card, to enable SSH daemon
  when started for first time. This is to avoid using an actual physical
  keyboard, mouse and screen.

- Plug-in the Raspberry Pi to your network and power it up. Use your router to
  find the dynamically allocated IP address (assuming your network has DHCP).

- SSH to the Raspberry Pi using the default credentials. The default user is _pi_
  the password is _raspberry_. E.g. `ssh pi@192.168.1.45` and enter `raspberry`.

- Run `sudo raspi-config` and:
    - Configure hostname (under _Network Options_).
        - I've used the naming convention `k8-master1` for the control plane
          node and `k8-slave1` for the nodes.
    - Enable SSH daemon (under _Interfacing Options_).
    - Update to latest version.

- Run `sudo nano /etc/dhcpcd.conf` and configure a static IP address.
    ````
    interface eth0
    static ip_address=192.168.1.102/24
    static routers=192.168.1.1
    static domain_name_servers=8.8.8.8 8.8.4.4
    ````

- Disable swap:
    ````
    sudo dphys-swapfile swapoff
    sudo dphys-swapfile uninstall
    sudo update-rc.d dphys-swapfile remove
    sudo apt purge dphys-swapfile
    ````
- Add Kubernetes repository to package sources:
    ````
    echo "deb http://apt.kubernetes.io/ kubernetes-xenial main" | sudo tee -a /etc/apt/sources.list.d/kubernetes.list
    curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | sudo apt-key add -
    ````

- Update available packages:
    ````
    sudo apt update
    ````

- Install useful network tools and Kubernetes ADM client:
    ````
    sudo apt install dnsutils kubeadm
    ````

- Install latest Docker:
    ````
    curl -sSL get.docker.com | sh && \
    sudo usermod pi -aG docker && \
    newgrp docker
    ````

- Configure ip tables to open all ports, currently required for [pod DNS to work](https://github.com/kubernetes-sigs/kubespray/issues/4674)):
    ````
    sudo iptables -P FORWARD ACCEPT
    ````
  Without this step, you may find `coredns` pods fail to start on app nodes
  and/or DNS does not work.

- Configure _iptables_ to run in legacy, since _kube-proxy_
  has [issues with iptables version 1.8](update-alternatives --set iptables /usr/sbin/iptables-legacy):
    ````
    sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
    ````

- Reboot `sudo reboot`.


### Control Plane Setup
The master node, responsible for controlling the cluster.

Based on steps from [official guide](https://kubernetes.io/docs/setup/production-environment/tools/kubeadm/create-cluster-kubeadm/).

I've named my control plane node _k8-master1_.

- SSH onto the Pi you want to be the master e.g. ssh `pi@k8-master`.

- Init cluster, with defined CIDR / network range for pods and a non-expiring token for slaves to join the cluster later:
    ````
    sudo kubeadm init --pod-network-cidr=10.244.0.0/16 --service-cidr=10.244.240.0/20 --token-ttl=0
    ````
- At the end of the previous step, copy the provided command for app nodes / slave
  to join the cluster. We'll run this later, it'll look something like:
    ````
    kubeadm join 192.168.1.100:6443 --token xxxxxx.xxxxxxxxxxxxxxxxx \
      --discovery-token-ca-cert-hash sha256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    ````

- Run the provided commands displayed at the end of the previous step.

- Install a pod network; in this case, we'll install Weave:
    ````
    kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
    ````


### Node Setup
The node(s), responsible for later running our application deployments.

Run the command from earlier to join the node to the cluster, either as root or prefix the command with `sudo`, for example:
    ````
    sudo kubeadm join 192.168.1.100:6443 --token xxxxxx.xxxxxxxxxxxxxxxxx \
        --discovery-token-ca-cert-hash sha256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    ````

This may take a few minutes.

Done.


## Kubernetes Dashbaord
Let's install a dashboard to easily manage our cluster.

Based on the [official guide](https://kubernetes.io/docs/tasks/access-application-cluster/web-ui-dashboard).

### Setup

- SSH to the master node e.g. `ssh pi@k8-master1`.

- Install the dashboard:

    ````
    kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.0-beta4/aio/deploy/recommended.yaml
    ````

- Paste the following in the file `user.yaml`:

    ````
    apiVersion: v1
    kind: ServiceAccount
    metadata:
      name: admin-user
      namespace: kube-system
    ---
    apiVersion: rbac.authorization.k8s.io/v1
    kind: ClusterRoleBinding
    metadata:
      name: admin-user
    roleRef:
      apiGroup: rbac.authorization.k8s.io
      kind: ClusterRole
      name: cluster-admin
    subjects:
    - kind: ServiceAccount
      name: admin-user
      namespace: kube-system
    ````

- Apply the file, it will setup a user for the dashboard:

    ````
    kubectl apply -f user.yaml
    ````

### Access
I've written a simple script to help automate accessing the dashboard.

This will:
- Proxy port _8080_ from the cluster to our local machine, in order to access
  the dashboard on our machine.
- Print out a token we can use to login to the dashboard.

Save the following script as `dashboard.sh`:
````
MASTER="pi@192.168.1.100"

# Print token for login
TOKEN_COMMAND="kubectl -n kube-system describe secret \$(kubectl -n kube-system get secret | grep admin-user | awk '{print \$1}')"

echo "Dumping token for dashboard..."
ssh ${MASTER} -C "${TOKEN_COMMAND}"

echo "Login:"
echo "  http://localhost:8080/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/#/login"

# Create SSH tunnel to k8 master and run proxy
echo "Creating proxy tunnel to this machine from master..."
ssh -L 8080:localhost:8080 ${MASTER} -C "kubectl proxy --port=8080 || true"

echo "Terminate proxy..."
ssh ${MASTER} -C "pkill kubectl"
````

Change the `MASTER` variable to the IP address or host-name of your control
plane node.

Run the script:
````
./dashboard.sh
````

Visit the URL displayed by the script to access the dashboard, and copy the
token on the logon screen.


## Installing Helm
Helm is a package manager for Kubernetes, which we'll use to setup
ingress in the next section.

It consists of two parts: _helm_ the client, _tiller_ the Helm server.

The server will be deployed as a pod, which needs a service account. Thus
create a file `rbac-config.yaml` with:

````
apiVersion: v1
kind: ServiceAccount
metadata:
  name: tiller
  namespace: kube-system
---
apiVersion: rbac.authorization.k8s.io/v1
kind: ClusterRoleBinding
metadata:
  name: tiller
roleRef:
  apiGroup: rbac.authorization.k8s.io
  kind: ClusterRole
  name: cluster-admin
subjects:
  - kind: ServiceAccount
    name: tiller
    namespace: kube-system
````

And apply the config:
````
kubectl apply -f rbac-config.yaml
````

Since we're using a Raspberry Pi with an _arm_ architecture, we'll install an
_arm_ version of the helm client on the master node and
a _multi-arch_ [image](https://github.com/jessestuart/tiller-multiarch) of _tiller_
on the cluster:

````
ssh pi@k8-master1
wget https://get.helm.sh/helm-v2.14.3-linux-arm.tar.gz
tar xvzf helm-v2.14.3-linux-arm.tar.gz
sudo mv linux-arm/helm /usr/local/bin/helm
rm -rf linux-arm
helm init --tiller-image=jessestuart/tiller:v2.14.3 --service-account tiller
````

## Ingress
We'll setup [ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/), so that
external traffic can reach our cluster.

Our end-goal will be for services to be accessible from the internet.

### Nginx
This section will setup our _ingress controller_, using NGINX, responsible
for managing anything related to ingress to our services.

Install using helm:
````
ssh pi@k8-master1
helm install stable/nginx-ingress --name nginx-ingress --namespace kube-ingress --set controller.publishService.enabled=true
````

This now has [official](https://github.com/kubernetes/ingress-nginx/pull/3852)
arm support, we just need to change the deployment images:

````
kubectl --namespace kube-ingress set image deployment/nginx-ingress-controller \
    nginx-ingress-controller=quay.io/kubernetes-ingress-controller/nginx-ingress-controller-arm:dev

kubectl --namespace kube-ingress set image deployment/nginx-ingress-default-backend \
    nginx-ingress-default-backend=gcr.io/google_containers/defaultbackend-arm:1.5
````

### Metal Loadbalancer
Since we aren't using a cloud provider, such as AWS, we don't have a cloud
load balancer available.

Instead we'll setup a load-balancer on our cluster, using MetalLB, which runs in a pod and
attaches its self onto a physical network IP.

You can either attach to an IP using Layer 2 (OSI Data Link layer), whereby only
a single node can handle traffic. This presents a resilience risk, but
offers the most support, and you'll definitely be able to access said IP from
your machine on the same network. Alternatively your cluster can, providing
your router supports it, use [BGP](https://en.wikipedia.org/wiki/Border_Gateway_Protocol),
but you'll most likely not be able to access the cluster locally.

Let's install MetalLB using helm:

````
helm install --namespace kube-ingress --name metallb stable/metallb
````

Based on the above, choose either _Layer 2_ or _BGP_.

#### Option 1: Layer 2
Create `metallb-config.yaml` to configure load balancer:

````
apiVersion: v1
kind: ConfigMap
metadata:
  namespace: kube-ingress
  name: metallb-config
data:
  config: |
    address-pools:
    - name: default
      protocol: layer2
      addresses:
      - 192.168.1.240-192.168.1.250
````

### Option 2: BGP
Create `metallb-config.yaml` to configure load balancer:

````
apiVersion: v1
kind: ConfigMap
metadata:
  namespace: kube-ingress
  name: metallb-config
data:
  config: |
    peers:
    - peer-address: 192.168.1.1
      peer-asn: 64501
      my-asn: 64500
    address-pools:
    - name: default
      protocol: bgp
      addresses:
      - 192.168.10.0/24
````

For more details on both options, refer to the [official docs](https://metallb.universe.tf/configuration/).

Just be careful, as we've got a different namespace to the docs.

Once config has been setup, apply it:
````
kubectl apply -f metallb-config.yaml
````


## Summary
In this article we setup a simple cluster capable of deployments and taking
external traffic.

For more details on my own cluster, see my later [post](/2019/10/13/home-automation).
