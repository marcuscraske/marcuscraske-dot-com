---
layout: post
title: Build TV
thumbnail: /assets/projects/build-tv/thumb.png
priority: 2015
selected: projects
---

A build TV project, which allows a Raspberry Pi to serve as an information radiator for a development environment.


# Features
- **Monitor Jenkins** - poll multiple Jenkins views/instances/jobs, useful in a large development environment.

- **LED strip** - ability to have a ws21x LED strip display patterns to indicate the build status, as well as standup
  and other events which can be customised.
  
- **Dashboards** - display a series of dashboards, which can each display either a web-page or a Jira wallboard.

- **Notifications** - display time-based notifications, which show an on-screen message and can change the LED strip
  to a defined pattern. Useful to notify the team of important events, such as daily standups.
  
- **Automated deployment** - using Ansible, updates and changes to configuration can be rolled-out within a single
  command to multiple build TVs. This also means new build TVs are fast to setup. Useful in a large development
  environment.

- **Modular architecture** - everything front-end is a client, with backend processes split into
  daemons by function, both sharing common libraries.
  
- **APIs** - all daemons expose their REST APIs, used for inter-process communication, through a remote daemon.
  The remote daemon is able to call an external service, such as your own website, with a randomly-generated token
  on startup. This means your own applications/services can be built on top of the build TV, and even reside
  on a remote machine, to provide remote control.
  
- **Stats** - a remote daemon service, running on each build TV, can report back statistics on its
  environment.

# Info &amp; Download

<https://github.com/limpygnome/build-tv>

# Gallery

<ul class="gallery">
    <li>
        <a href="/assets/projects/build-tv/jira-wallboard.png">
            <img src="/assets/projects/build-tv/jira-wallboard.png" alt="Build TV showing Jira wallboard" title="Build TV showing Jira wallboard" />
            Build TV showing Jira wallboard
        </a>
    </li>
    <li>
        <a href="/assets/projects/build-tv/build-status-dashboard.jpg">
            <img src="/assets/projects/build-tv/build-status-dashboard.jpg" alt="Build status dashboard" />
            Build status dashboard
        </a>
    </li>
    <li>
        <a href="/assets/projects/build-tv/share-price-dashboard.jpg">
            <img src="/assets/projects/build-tv/share-price-dashboard.jpg" alt="Share price dashboard" />
            Share price dashboard
        </a>
    </li>
    <li>
        <a href="/assets/projects/build-tv/standup-notification.jpg">
            <img src="/assets/projects/build-tv/standup-notification.jpg" alt="Standup notification" />
            Standup notification
        </a>
    </li>
</ul>
