---
layout: post
title: Raspberry Pi Kubernetes Cluster
selected: blog
updated: 2022-12-13
---

{% include image.html path="/assets/posts/2019-09-21-raspberry-pi-kubernetes-cluster/thumb.png" alt="K8 and Pi logo" class="post-thumb" %}

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

- [Download the latest 64-bit Raspbian image](https://www.raspberrypi.org/downloads/raspbian) and
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
          node and `k8-slave1` to n for the nodes.
    - Enable SSH daemon (under _Interfacing Options_).
    - Update to latest version.

- Run `sudo nano /etc/dhcpcd.conf` and configure a static IP address (change `static ip_address` line for each node).
    ````
    interface eth0
    static ip_address=192.168.1.100/24
    static routers=192.168.1.1
    static domain_name_servers=192.168.1.1
    ````

- Disable swap:
    ````
    sudo dphys-swapfile swapoff
    sudo dphys-swapfile uninstall
    sudo update-rc.d dphys-swapfile remove
    sudo apt purge dphys-swapfile
    ````

- Enable memory cgroups, run `sudo nano /boot/cmdline.txt` and append the following to the end of the line:
    ````
    cgroup_enable=cpuset cgroup_enable=memory cgroup_memory=1 swapaccount=1
    ````

- Reboot, connect using the new IP address.

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
    sudo apt install dnsutils kubelet kubeadm kubelet
    sudo apt-mark hold kubelet kubeadm kubectl
    ````

- Install latest Docker:
    ````
    curl -sSL get.docker.com | sh && \
    sudo usermod pi -aG docker && \
    newgrp docker
    ````

- Configure ip tables to open all ports, currently required for [pod DNS to work](https://github.com/kubernetes-sigs/kubespray/issues/4674).
  Start by editing:
    ````
    sudo nano /etc/rc.local
    ````
  And add (before any lines with _exit_):
    ````
    /sbin/iptables -P FORWARD ACCEPT
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

- SSH onto the Pi you want to be the master e.g. `ssh pi@k8-master`.

- Init cluster, with defined CIDR / network range for pods and a non-expiring token for slaves to join the cluster later:
    ````
    sudo kubeadm init --pod-network-cidr=10.244.0.0/16 --service-cidr=10.244.240.0/20 --token-ttl=0
    ````

  If this command fails due to container runtime not running, run the following commands:
  ````
  sudo -i
  rm /etc/containerd/config.toml
  containerd config default > /etc/containerd/config.toml
  ````
  
  And search for `SystemdCgroup` param and set it to true:
  ````
  sudo nano /etc/containerd/config.toml
  ````
  
  And restart containerd:
  ````
  sudo systemctl restart containerd
  ````

  And run the previous `kubeadm` command again.

  This seems to be an issue with containerd not using cgroup, see [GitHub issue](https://github.com/kubernetes/kubernetes/issues/110177)
  for details.

- At the end of the previous step, copy the provided command for app nodes / slave
  to join the cluster. We'll run this later, it'll look something like:
    ````
    kubeadm join 192.168.1.100:6443 --token xxxxxx.xxxxxxxxxxxxxxxxx \
      --discovery-token-ca-cert-hash sha256:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
    ````

- Enable your user to use `kubectl`:
    ````
    mkdir -p $HOME/.kube
    sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
    sudo chown $(id -u):$(id -g) $HOME/.kube/config
    ````

- Install pod networking (CNI); in this case, we'll install Flannel on the master and each slave:
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
    ---
    apiVersion: v1
    kind: Secret
    metadata:
      name: secret-admin-user
      namespace: kube-system
      annotations:
        kubernetes.io/service-account.name: "admin-user"
    type: kubernetes.io/service-account-token
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

````bash
MASTER="pi@192.168.1.100"

# Print token for login
TOKEN_COMMAND="kubectl -n kube-system describe secret/secret-admin-user | grep 'token: ' | awk '{ print \$2 }'"

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

Let's install the client:

````
ssh pi@k8-master1
wget https://get.helm.sh/helm-v3.9.2-linux-arm64.tar.gz
tar -zxvf helm-v3.9.2-linux-arm64.tar.gz
sudo mv linux-arm64/helm /usr/local/bin/helm
````


## Ingress
We'll setup [ingress](https://kubernetes.io/docs/concepts/services-networking/ingress/), so that
external traffic can reach our cluster.

Our end-goal will be for services to be accessible from the internet.


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
kubectl create namespace metallb-system
helm repo add metallb https://metallb.github.io/metallb
helm install metallb metallb/metallb --namespace metallb-system
````

Create `metallb-config.yaml` to configure load balancer:

````
apiVersion: metallb.io/v1beta1
kind: IPAddressPool
metadata:
  name: main-pool
  namespace: metallb-system
spec:
  addresses:
  - 192.168.1.230-192.168.1.250
---
apiVersion: metallb.io/v1beta1
kind: L2Advertisement
metadata:
  name: main-pool
  namespace: metallb-system
spec:
  ipAddressPools:
  - main-pool
````

For more details on options, as BGP is available instead of Layer 2, refer to
the [official docs](https://metallb.universe.tf/configuration/).

Once config has been setup, apply it:

````
kubectl apply -f metallb-config.yaml
````

Ingress is now setup.


### Nginx
This section will setup our _ingress controller_, using NGINX, responsible
for managing anything related to ingress to our services. This was originally used for the project in 2019. But as of
2022, I've moved to istio, you can skip ahead to the section called _istio_ below.

Install using helm:
````
ssh pi@k8-master1
kubectl create namespace kube-ingress
helm repo add nginx-stable https://helm.nginx.com/stable
helm repo update
helm install nginx-ingress nginx-stable/nginx-ingress --namespace kube-ingress --set controller.service.loadBalancerIP=192.168.1.230
````


### Ingress Example
You can now define an ingress to a service.

An example which forwards traffic for _marcuscraske.com/uptime/**_ to the service _uptime_.

````
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  namespace: home-network
  name: uptime-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - host: marcuscraske.com
    http:
      paths:
      - path: /uptime
        backend:
          serviceName: uptime
          servicePort: 80
````

Using the Kubernetes dashboard, or _kubeadm_ tool, you can find the local network
IP address allocated.

Then port forward traffic on your router for the IP address, so that it's exposed to the internet.

If you want multiple ingress points to share the same IP address, you can add config to your
services to get a defined IP address allocated.

Take note of the annotations section, `type` and `loadBalancerIP` params in this example:

````
kind: Service
apiVersion: v1
metadata:
  name: uptime
  annotations:
    metallb.universe.tf/address-pool: main-pool
spec:
  selector:
    app: uptime
  ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
  sessionAffinity: None
  type: LoadBalancer
````

You can then front your router's WAN / public internet IP address with a CDN such as
[CloudFlare](https://www.cloudflare.com), which is free and offers DDOS protection (useful for a home network).
This will also provide valid SSL certificates.


## Istio (2022)
An alternative to nginx, you can use istio with istio gateway.

Relevant docs:
- <https://istio.io/latest/docs/setup/install/helm/>
- <https://istio.io/latest/docs/setup/additional-setup/gateway/>

We'll start by installing istio:

````
ssh pi@k8-master1

helm repo add istio https://istio-release.storage.googleapis.com/charts
helm repo update

kubectl create namespace istio-system
helm install istiod istio/istiod -n istio-system --wait
````

You'll want to wait until all the pods related to istio are running and ready:

````
kubectl get pods -A
````

We'll next install the gateway, change the IP address to the desired IP for exposing services on your local
network:

````
kubectl create namespace istio-ingress
helm install istio-ingressgateway istio/gateway -n istio-ingress --set service.loadBalancerIP="192.168.2.199"
````

Rather than add an ingress for exposing a service, we'll first create a namespace and a shared gateway, which will
ingress traffic to the domain `smart.home.local`:

````
apiVersion: v1
kind: Namespace
metadata:
  name: smart-home
  labels:
    name: smart-home
---
apiVersion: networking.istio.io/v1alpha3
kind: Gateway
metadata:
  namespace: smart-home
  name: smart-home-gateway
spec:
  selector:
    istio: ingressgateway
  servers:
  - port:
      number: 80
      name: http
      protocol: HTTP
    hosts:
    - "smart.home.local"
````

And then in each service we want to expose, we can add a virtual service to tie it to the gateway, with this example
rewriting the request to remove the prefix/path of the route:

````
apiVersion: networking.istio.io/v1alpha3
kind: VirtualService
metadata:
  namespace: smart-home
  name: example
spec:
  hosts:
  - "smart.home.local"
  gateways:
  - smart-home-gateway
  http:
  - match:
    - uri:
        prefix: /api/example
    rewrite:
      uri: " "
    route:
    - destination:
        port:
          number: 80
        host: example-service
````


## Check Pods
You can check pods across all namespaces/nodes are running:

````
ssh pi@k8-master1
kubectl get pods --all-namespaces
````


## Shared Data
_This section is optional, and was added later in 2022._

You might want to mount a file share between all the worker nodes, so they can share config. This example uses
CIFs/Samba.

SSH to the worker:

````
ssh pi@k8-slave1
````

Switch to root:

````
sudo -i
````

Setup the desired location to mount data:

````
mkdir -p /mnt/k8-data
chown -R pi:pi /mnt/k8-data/
````

Grab the number from this command e.g. 1000, this will be used later for the `gid` and `uid`:

````
getent group pi
````

Add user to group:

````
usermod -a -G k8data pi
````

Test the mount will work, changing the IP of the host with the file share and login user/pass:

````
mount.cifs -v //192.168.1.200/k8-data/k8-slave1 /mnt/k8-data --verbose -o user=kubernetes,pass=xxx,gid=1000,uid=1000
````

Providing the above works, now add a new line to `/etc/fstab`, so that it's automatically mounted on each boot:

````
//192.168.1.200/k8-data/k8-slave1 /mnt/k8-data cifs username=kubernetes,password=xxx,gid=1000,uid=1000 0 0
````


## Summary
In this article we setup a simple cluster capable of deployments and taking
external traffic.
