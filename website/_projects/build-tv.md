---
layout: post
title: Build TV
thumbnail: /assets/projects/build-tv/thumb.png
priority: 2015
---

A build TV project, which allows a Raspberry Pi 2 to serve as an information radiator for a development environment.
Display information from Jira, continuous integration systems and display time-based notifications for events such as
daily standup.

# Features
- <b>Monitor Jenkins</b> - poll multiple Jenkins views/instances/jobs, useful in a large development environment.

- <b>LED strip</b> - ability to have a ws21x LED strip display patterns to indicate the build status, as well as standup
  and other events which can be customised.
  
- <b>Dashboards</b> - display a series of dashboards, which can each display either a web-page or a Jira wallboard.

- <b>Notifications</b> - display time-based notifications, which show an on-screen message and can change the LED strip
  to a defined pattern. Useful to notify the team of important events, such as daily standups.
  
- <b>Automated deployment</b> - using Ansible, updates and changes to configuration can be rolled-out within a single
  command to multiple build TVs. This also means new build TVs are fast to setup. Useful in a large development
  environment.

- <b>Modular architecture</b> - everything front-end is a client, with backend processes split into
  daemons by function, both sharing common libraries.
  
- <b>APIs</b> - all daemons expose their REST APIs, used for inter-process communication, through a remote daemon.
  The remote daemon is able to call an external service, such as your own website, with a randomly-generated token
  on startup. This means your own applications/services can be built on top of the build TV, and even reside
  on a remote machine, to provide remote control.
  
- <b>Stats</b> - a remote daemon service, running on each build TV, can report back statistics on its
  environment.

# Download, Documentation &amp; Setup
The code, along with markup documentation and releases, can be found at:
[https://github.com/limpygnome/build-tv]

# Gallery

<ul class="gallery">
    <li>
        <a href="/assets/projects/build-tv/jira-wallboard.png">
            <img src="/assets/projects/build-tv/jira-wallboard.png" alt="Build TV showing Jira wallboard" title="Build TV showing Jira wallboard" />
            Build TV showing Jira wallboard
        </a>
    </li>
</ul>
