---
layout: post
title: Smart Home
selected: blog
---

{% include image.html path="/assets/posts/2019-09-21-raspberry-pi-kubernetes-cluster/thumb.png" alt="Smart home dashboard" class="post-thumb" %}

Automated lighting, hoovering, heating, energy management and information. A combination of off-the-shelf and
hand-cranked code running on a Kubernetes cluster.

Started in December 2019, and evolving throughout the UK lockdown's of 2020 and 2021.

## Dashboard
node app on raspberry pi

microservices written in node using express.js


## Microservices
<p class="center">
    {% include image.html path="/assets/projects/smart-home/microservices.png" alt="Microservice diagram" %}
</p>

Why microservices?

Admittedly overkill for a project of this size. Most of the services are only a few scripts large, but each one has a
clear purpose, and can fail without taking down the entire ecosystem. Updates can be easily performed to a single
service, without affecting the overall functionality. For instance, updating the _Dashboard_ without affecting
automation scripts, which are otherwise performing actions to the entire home. And if an update fails, most of the
functionality continues to work, and can be fixed another day.

Why run on Kubernetes?

They needed to run on something, and reliability is important, especially for _Home Actions_. Using a Raspberry Pi
makes sense for running the scripts, due to the low power consumption, but they're notorious for failing due to the
MicroSD cards. And when it comes to maintaining the application node, they can be updated independently, and regularly,
with deployments  shifted to other nodes in the event of any problems. Again, if there's a problem, it can be fixed
another day.

But ultimately, a bunch of JavaScript controlling your house, on a Kubernetes cluster, is a flex.


## Automation Scripts
The _Home Actions_ service had a few automated scripts, consuming data and performing actions on the _Power Plugs_
and _Philip Hue_ services:

- __Turning on the treadmill fan,__ when the treadmill was used, based on the power consumption of the treadmill smart 
  plug going above an idle threshold (in watts).
- __Turning off the living room lights and motion sensor,__ when the projector is turned-on, again based on plug power
  consumption, but for the projector.
- __Turning off all the smart plugs when the house is idle,__ based on 30 minutes of inactivity on all the motion
  sensors, which are in every single room. And then turning them back on, when motion is detected again.


## Appliances
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

## Conclusion
A lot of the custom aspects of the project could have used other off-the-shelf projects, such as Apple HomeKit.

Despite the time and cost, I'm unfortunately now moving home, I'll migrate most of it to the new place.

Whilst it was definitely 

