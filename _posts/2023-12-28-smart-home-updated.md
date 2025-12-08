---
layout: post
title: Smart Home: Updated
selected: blog
---

If you haven't already, checkout [the previous post about the smart home](2022-02-19-smart-home.md)).

Since the original article, I've since moved home to London, and made a few updates.

In summary:
- New hardware.
- Move to Zigbee2mqtt.
- New features.
- Logging (Vector/Loki/Grafana).



## New Hardware
[pic here of rack unit]

Due to moving house, the majority of the hardware has been changed.

The picture above shows the rack mount unit for the Kubernetes cluster and network stack, as to reduce space.

Almost everything is now using Zigbee, as to continue to function without a reliance on either internet or a third-party service.

A list of Zigbee hardware in use, which I can recommend:
- Frient:
    - Air quality sensor
    - Water leak detector
    - Plugs
- Philip Hue:
    - Lights
    - Motion sensors
    - Wall buttons
- Aqua:
    - Temperature sensors
- IKEA:
    - Air purifiers


I'm also using a Glow XXX (TBC) monitor, which provides an MQTT topic for subscribing to the overall power usage of a home. It can
also be used for gas, although I haven't used this function. It also provides the unit cost of energy as well. The only downside is that
it relies on an internet connection to pull data.

_Note:_ none of the above devices are using the bridges provided by the manufacturer, this is not needed.


## Move to Zigbee2mqtt

### Setup Zigbee2mqtt
Create the deployment:

````
apiVersion: apps/v1
kind: Deployment
metadata:
  namespace: zigbee2mqtt
  name: zigbee2mqtt
  labels:
    app: zigbee2mqtt
spec:
  replicas: 1
  selector:
    matchLabels:
      app: zigbee2mqtt
  template:
    metadata:
      labels:
        app: zigbee2mqtt
    spec:
      hostNetwork: true
      containers:
      - name: mqtt
        image: eclipse-mosquitto:2.0
        ports:
        - containerPort: 1883
        - containerPort: 9001
        command: ["mosquitto", "-c", "/mosquitto/config.conf"]
        volumeMounts:
          - mountPath: /mosquitto
            name: mosquitto-config
      - name: zigbee2mqtt
        securityContext:
          privileged: true
        image: koenkk/zigbee2mqtt
        ports:
          - containerPort: 8080
        env:
          - name: TZ
            value: "Europe/London"
        volumeMounts:
          - mountPath: /dev/ttyUSB0
            name: sonoffusbdevice
          - mountPath: /app/data
            name: zigbee2mqtt-config
      nodeSelector:
        kubernetes.io/hostname: k8-slave1
      volumes:
        - name: sonoffusbdevice
          hostPath:
            path: /dev/ttyUSB0
        - name: mosquitto-config
          hostPath:
            path: /mnt/k8-data/zigbee2mqtt/mosquitto
        - name: zigbee2mqtt-config
          hostPath:
            path: /mnt/k8-data/zigbee2mqtt/zigbee2mqtt
---
kind: Service
apiVersion: v1
metadata:
  namespace: zigbee2mqtt
  name: zigbee2mqtt
  annotations:
    metallb.universe.tf/loadBalancerIPs: 192.168.2.120
spec:
  selector:
    app: zigbee2mqtt
  ports:
    - name: frontend
      protocol: TCP
      port: 8080
      targetPort: 8080
    - name: mqtt
      protocol: TCP
      port: 1883
      targetPort: 1883
  type: LoadBalancer
---
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  namespace: zigbee2mqtt
  name: zigbee2mqtt-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: zigbee2mqtt
                port:
                  number: 8080

````


## New Features
[pic of sentinel text messages on a mobile]
__Sentinel:__ a service which looks at all the sensors for any values falling outside of configured warning and critical thresholds, which sends text messages (SMS) via Twilio upon breach.


[pic of improvement on dashboard]
__Transport:__ improved to pull data from Transport for London's (TF's') public API, showing the overall line status of the TFL network and the departure times for a few nearby stations.


## Logging (Vector/Loki/Grafana)
Previously, there was no logging or telemetry.

### Setup Vector
Create a file called `values.yaml`:

````
role: "Agent"

customConfig:
  data_dir: /vector-data-dir
  api:
   enabled: true
   address: 127.0.0.1:8686
   playground: false
  sources:
    kubernetes_logs:
      type: kubernetes_logs
  transforms:
    json:
      type: remap
      inputs:
        - kubernetes_logs
      source: |-
        json = parse_json!(.message)
        . = merge(., json) ?? .
    keyvalue:
      type: remap
      inputs:
        - kubernetes_logs
      source: |-
        kv = parse_key_value!(.message)
        . = merge(., kv)
  sinks:
    grafana_loki:
      type: loki
      inputs: ["json"]
      endpoint: "http://logs.home:3100"
      encoding:
        codec: json
      labels:
        forwarder: vector
        k8_namespace: "{{`{{ kubernetes.pod_namespace }}`}}"
        k8_pod_name: "{{`{{ kubernetes.pod_name }}`}}"
        k8_pod_node_name: "{{`{{ kubernetes.pod_node_name }}`}}"
        k8_container_name: "{{`{{ kubernetes.container_name }}`}}"
        stream: "{{`{{ stream }}`}}"

````

Deploy Vector into the Kubernetes cluster, as a daemon set (side cart for other pods):

````
# Add Vector as a helm repository
helm repo add vector https://helm.vector.dev
helm repo update

# Install Vector and use config created above
helm install vector vector/vector --namespace vector --create-namespace --values values.yaml
helm upgrade vector vector/vector --namespace vector --values values.yaml

# Needed for daemonsets (side car updates) to be picked up
kubectl rollout restart daemonset/vector -n vector
````




grafana loki
grafana

