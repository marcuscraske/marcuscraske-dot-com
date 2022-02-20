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

Started in December 2019, and evolving throughout the UK lockdown's of 2020 and 2021.

## Dashboard

<p class="center">
    {% include image.html path="/assets/posts/2022-02-19-smart-home/dashboard.png" alt="Dashboard on TV" %}
</p>

node app on raspberry pi

microservices written in node using express.js


## Microservice Architecture

<p class="center">
    {% include image.html path="/assets/posts/2022-02-19-smart-home/microservices.png" alt="Microservice diagram" %}
</p>


## Why Microservices?

Admittedly overkill for a project of this size.

Most of the services are only a few scripts large, but each one has a
clear purpose, and can fail without taking down the entire ecosystem. This is important for one service in
particular, which interfaces with a device using USB, and could be prone to operating-system issues, such as locking
up the service.

Updates can be easily performed to a single service, without affecting the overall functionality. For instance, updating
the _Dashboard_ without affecting automation scripts, which are otherwise performing actions to the entire home. And if
an update fails, most of the functionality continues to work, and can be fixed another day.


## Why Kubernetes?

The microservices needed to run on something, and reliability is important, especially for _Home Actions_.

Using a Raspberry Pi makes sense for running the scripts, due to the low power consumption, but they're notorious for
failing due to MicroSD cards burning out. A single instance could leave the entire system not working for days,
especially if there isn't time to fix it.

Instead, Kubernetes allows for building a distributed system, spreading loads across multiple
instances (Raspberry Pi's), useful in the event of maintenance and failure. It also reduces the risk of maintenance,
allowing for each instance to be patched and re-introduced one at a time, and abandoned in the event of issues.

This project was a good example to build on a previous post,
about [running a Kubernetes cluster on Raspberry Pi's](/2019/09/21/raspberry-pi-kubernetes-cluster/).


## The Services

__Dashboard:__ xxx

__Bin Collection:__ perhaps known as garbage collection outside the UK, is usually
every week for my home. But the days can vary between Friday and Saturday, and become even stranger around public
holidays. A _council_ is responsible for the collection of garbage, a local government organisation. And collection
dates are published online. Unfortunately, they don't provide a public API, so the service scrapes their website
instead, reading the data from the page using jQuery (LOL), and provides a JSON API for other services.

__Weather:__ xxx

__Uptime:__ xxx

__Trains:__ data is retrieved, for the station closest to my home, using the [National Rail Live Departure Boards Web
Service](https://wiki.openraildata.com/index.php/NRE_Darwin_Web_Service_(Public)) (LDBWS) SOAP API, . It's the same API
used by the National Rail Enquiries website. And the data is derived from the [National Rail
Darwin](https://wiki.openraildata.com/index.php?title=TRUST_vs_Darwin#Darwin) system, used for the departure boards
across UK stations.

__Share Price:__ xxx

__Philip Hue:__ xxx

__Power Plugs:__ xxx

__Power Usage:__ xxx

__Home Actions:__ running automated scripts, more detail in the next section.


## Automation Scripts
The _Home Actions_ service had a few automated scripts, consuming data and performing actions on the _Power Plugs_
and _Philip Hue_ services:

- __Turning on the treadmill fan,__ when the treadmill was used, based on the power consumption of the treadmill smart 
  plug going above an idle threshold (in watts).
- __Turning off the living room lights and motion sensor,__ when the projector is turned-on, again based on plug power
  consumption, but for the projector.
- __Turning off all the smart plugs when the house is idle,__ based on 30 minutes of inactivity on all the motion
  sensors, which are in every single room. And then turning them back on, when motion is detected again.


## Smart Appliances
_This section is not sponsored, I promise..._

__Philip Hue lighting__ throughout the entire interior and exterior of the house. Motion sensors in every room
automatically turn on, and eventually off, every single light. Each bulb is ultra-energy efficient, using 7 watts each.
And an RGB LED floodlight, using 15 watts, for the outdoor carpark space. And front door light synchronised with
sunset/sunrise.

__Roborok S5 Max Robovac__ both upstairs and downstairs, running twice a week, cleaning the floors, including mopping.
Requiring only fortnightly maintenance: emptying of the onboard bin, cleaning the brush and refilling the water tank.
Although I suspect it wouldn't do well with animal hair, as human hair was enough to cripple it at times.

__Hive Smart Heating__ isn't particularly different to a time-controlled thermostat, other than it can be controlled
through a mobile app, and run different schedules for different days. Super useful to turn off the heating when going
on holiday, and turning it on remotely before getting home.

__Foscam IP Cameras__ were an unusual choice, but I wanted something that worked entirely offline due to privacy
concerns. These cameras are well-established in corporate environments due to reliability, and often appeared on leaked
camera lists, on forums and discussion boards, when they were misconfigured. Although if I were to pick new cameras,
I'd probably look at Reolink. In total, I had 2x 1080p and 1x 1440p (2k) cameras, recorded using
[MotionEyeOS](https://github.com/ccrisan/motioneyeos/wiki), at 12 FPS, with footage stored for two weeks,
in a locked cabinet on WD Purple NVR drives (in RAID1), and automatically uploaded to Dropbox.

__Tuya Smart Plugs__ providing individual power usage and could be turned on/off remotely.

All of the above appliances can be accessed through smart apps, what could go wrong? ;)


## Photos

<ul class="gallery">
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/bridges.png"
                              description="Philip Hue and Hive bridges, used for remote control" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2022-02-19-smart-home/cabinet.png"
                              description="Rack mounted into a 42U server cabinet" %}
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
A lot of the custom aspects of the project could have used other off-the-shelf projects, such as Apple HomeKit.

Despite the time and cost, I'm unfortunately now moving home, I'll migrate most of it to the new place.

Whilst it was definitely 

