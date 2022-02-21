---
layout: post
title: Smart Home
selected: blog
---

{% include image.html path="/assets/posts/2022-02-19-smart-home/microservices.png"
                      alt="Smart home dashboard"
                      class="post-thumb" %}

Automated lighting, hoovering, heating, energy management and information. A combination of off-the-shelf and
hand-cranked code running on a Kubernetes cluster.

Started in December 2019, and evolved throughout the UK lockdown's of 2020 and 2021.

## Dashboard

<p class="center">
    {% include image.html path="/assets/posts/2022-02-19-smart-home/dashboard.png" alt="Dashboard on TV" %}
</p>

The centrepiece of the smart home, located near the front door. Lots of data is aggregated, both
from within the home and external sources, used for checking: weather, train times, share price of the S&P 500 and
state of the home. Useful for when leaving and arriving home.

The floor plan shows the colour of the lights, and temperature, in each room, with some lights supporting RGB, and
motion sensors, with those recently activated in green.

The TV its self is just using a Raspberry Pi, with an Electron (Node.js) web browser launching on startup. The app
displays an external web-page, and polls an externally hosted service to check whether to turn the screen off, when
there's no motion in the room.

The externally hosted resources are on a Kubernetes cluster, which we'll delve into in the upcoming sections.


## Microservice Architecture

The data displayed on the dashboard is derived from multiple microservices:

<p class="center">
    {% include image.html path="/assets/posts/2022-02-19-smart-home/microservices.png" alt="Microservice diagram" %}
</p>


## Why Microservices?

Admittedly overkill for a project of this size.

Most of the services are only a few scripts large, but each one has a
clear purpose, and can fail without taking down the entire ecosystem. This is important for one service in
particular, which interfaces with a device using USB, and can be prone to hardware issues, causing the service
to hang or slow-down.

Updates can be easily performed to a single service, without affecting the overall functionality. For example, updating
the _Dashboard_ without affecting automation scripts, which are otherwise performing actions on the home. And if
an update fails, most of the functionality continues to work, and can be fixed another day.


## Why Kubernetes?

The microservices needed to run on something, and reliability is important, especially for _Home Actions_, which is
running automated scripts to control the home.

Using a Raspberry Pi makes sense for running the microservices, due to the low power consumption, but they're notorious
for failing due to MicroSD cards burning out. Running on a single instance could leave the entire system not working for
days, or longer, if there isn't time to rectify problems.

Instead, Kubernetes allows for building a distributed system, spreading loads across multiple
instances (Raspberry Pi's), useful for avoiding such failure. It reduces the risk of regular maintenance,
allowing for each instance to be patched and re-introduced one at a time, and removed in the event of issues.

This project was also a good example to build on a previous post,
[running a Kubernetes cluster on Raspberry Pi's](/2019/09/21/raspberry-pi-kubernetes-cluster/).


## Service Stack
Each service uses the _node_ (Node.js) Docker container image, using [Express](https://expressjs.com/) for building
simple APIs. Since we're running these images on Raspberry Pi's, with the ARM instruction set, image builds
are done remotely over SSH on the Kubernetes master instance.


## The Services

__Dashboard:__ displaying data from the other services, as seen on the TV shown previously, accessible to any device on
both the local network and internet. Built using React, with each section built as its own one or more components.

__Bin Collection:__ perhaps known as garbage collection outside the UK, is usually
every week. But the days can switch between Friday and Saturday, and become even stranger around public
holidays. A _council_ is responsible for the collection of garbage, a local government organisation. And collection
dates are published online. Unfortunately, they don't provide a public API, so the service scrapes their website
instead, reading the data from the page. The service then provides a simple JSON API for the other services.

__Weather:__ sourced for free from [OpenWeather](https://openweathermap.org/), this was the most accurate of a few
services trialed over a period of time, and able to provide predictions for a few days, down to individual hours.

__Uptime:__ pinging the Google homepage every second, used to track internet problems within the last 24 hours.

__Trains:__ data is retrieved, for the station closest to my home, using the [National Rail Live Departure Boards Web
Service](https://wiki.openraildata.com/index.php/NRE_Darwin_Web_Service_(Public)) (LDBWS) SOAP API, . It's the same API
used by the National Rail Enquiries website. And the data is derived from the [National Rail
Darwin](https://wiki.openraildata.com/index.php?title=TRUST_vs_Darwin#Darwin) system, the same data used for the
departure boards across UK stations.

__Share Price:__ retrieving share price information, updating every five minutes, for a given symbol. This used the
free [Finnhub Stock API](https://finnhub.io/). Although, I only ever displayed _SPY_ (S&P 500).

__Philip Hue:__ all the lights and motion sensors use Philip Hue, which has a bridge/hub device for controlling
and retrieving state information. This service throttled requests, as well as simplifying bulk interaction,
such as with a room. Throttling is important, as the bridge is incredibly underpowered, and it doesn't take many
requests to inadvertently cause a denial of service attack. Which happened a few times at the start of the project,
and the lights wouldn't turn on automatically in the rooms...like being back in the stone age, or what most
typical homes still have today.

__Power Plugs:__ almost every appliance has a smart plug, which can measure the power usage in watts, and turn
on/off power, using the Tuya cloud API. Huge kudos to the [tuyapi](https://github.com/codetheweb/tuyapi) project
for simplifying connectivity.

__Power Usage:__ this is one of the interesting sources of information, as it provides the power usage for the entire
house. British Gas, many years ago, were handing out the [CC128](http://www.currentcost.com/product-cc128.html) energy
monitor for free. You just put a clamp, hooked up to a wireless battery pack, around the mains electric,
in the outdoor utility box. This is able to measure the total power usage by measuring the magnetic field
of the cable, using the [hall effect](https://en.wikipedia.org/wiki/Hall_effect#Split_ring_clamp-on_sensor). It turns
out the CC128 has a serial bus on the back, which outputs the power usage as XML. You can buy a CC128 on eBay, and just
need a USB to serial adapter. This unfortunately means the service must be tied to a fixed instance (Raspberry Pi).

__Home Actions:__ running automation scripts, more detail in the next section.


## Automation Scripts
The _Home Actions_ service had a few automated scripts, consuming data and performing actions on the _Power Plugs_
and _Philip Hue_ services:

- __Turning on the treadmill fan,__ when the treadmill was used, based on the power consumption of the treadmill smart 
  plug going above an idle threshold (in watts).
- __Turning off the living room lights and motion sensor,__ when a projector is turned-on, again based on plug power
  consumption, but for the projector.
- __Turning off all the smart plugs when the house is idle,__ based on 30 minutes of inactivity on all the motion
  sensors, which are in every single room. And then turning them back on, when motion is detected again.
- __Turning on the (dashboard) TV when motion is detected in the kitchen,__ saving a significant amount of energy.

## Smart Appliances
_This section is not sponsored, I promise..._

__Philip Hue lighting__ throughout the entire interior and exterior of the house. Motion sensors in every room
automatically turn on, and eventually off, every single light. Each bulb is ultra energy efficient, using 7 watts each.
A typical LED bulb uses 9 watts, or incandescent bulb uses 100 watts. And an RGB LED floodlight, using 15 watts,
for the outdoor carpark space - a typical halogen floodlight uses 200-400 watts. And a front door light, synchronised
with sunset/sunrise.

__Roborok S5 Max Robovac__ both upstairs and downstairs, running twice a week, cleaning the floors, including mopping.
Requiring only fortnightly maintenance: emptying of the onboard bin, cleaning the brush and refilling the water tank.
Although, I suspect it wouldn't do well with animal hair, as human hair was enough to cripple it at times.

__Hive Smart Heating__ isn't particularly different to a time-controlled thermostat, other than it can be controlled
through a mobile app, and run different schedules for different days. Super useful to turn off the heating when going
on holiday, and turning it on remotely before getting home.

__Foscam IP Cameras__ were an unusual choice, but I wanted something that worked entirely offline due to privacy
concerns. These cameras are well-established in corporate environments due to reliability, and often appeared on leaked
camera lists, on forums and discussion boards, when they were misconfigured. Although if I were to pick new cameras,
I'd probably look at Reolink. In total, I had 2x 1080p and 1x 1440p (2k) cameras, recorded using
[MotionEyeOS](https://github.com/ccrisan/motioneyeos/wiki), at 12 FPS, with footage stored for two weeks,
in a locked cabinet on WD Purple NVR drives (in RAID1), and automatically uploaded to Dropbox.

__Tuya Smart Plugs__ providing individual power usage, and control, of appliances remotely.

Most of the above can be accessed through smart apps over the internet, what could go wrong? ;)


## Photos

<ul class="gallery">
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/bridges.png"
                              description="Philip Hue and Hive bridges, used for remote control" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/cabinet.png"
                              description="Rack mounted in a 42U server cabinet, although the cabinet is not exclusively for this project" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/heating-receiver.png"
                              description="Hive heating receiver" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/heating-thermostat.png"
                              description="Hive heating thermostat control" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/light-covered.png"
                              description="Philip Hue mount over normal light switch, with magnetic removable remote" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/light-uncovered.png"
                              description="Philip Hue mount cover removed" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/pi-rack.png"
                              description="Raspberry Pi 1U rack mount shelf" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/robovac-downstairs.png"
                              description="Robovac (downstairs)" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/robovac-upstairs.png"
                              description="Robovac (upstairs)" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/smart-plugs.png"
                              description="Some of the smart plugs" %}
    </li>
</ul>

## Conclusion
A lot of the custom aspects of the project could have used off-the-shelf platforms, such as
[Apple HomeKit](https://www.apple.com/uk/ios/home/) or [openHAB](https://www.openhab.org/).

But what value does custom bring?

1. Integrating and scripting different platforms, which openHAB probably does far easier.
2. The dashboard, which is completely customisable, and one of the main reasons for this project.
3. Learning.
4. Flexing that one's house runs on JavaScript.

What about maintenance and issues?

- A home automation script had a bug, which spammed the Philip Hue service, and caused a denial of service attack.
  Since the remote switches and motion sensors relied on the bridge, I had to stumble through the house with my phone
  torch to get to my desk to undeploy the service. This was later rectified by fixing said bug, and throttling the
  Philip Hue service as a fail-safe.
- The certificates used by Kubernetes expired, causing the Kubernetes API to stop working, along with service
  communication, which includes internet and local connectivity. This was later fixed by renewing the certificates.

Overall, it's been an interesting ongoing project for the last two years. And despite moving house soon, I will
be carrying forward this setup.
