<h1>
    Overview
</h1>
<p>
    An <a href="https://github.com/limpygnome/build-tv">open source</a> build TV project, which allows a Raspberry Pi 2
    to serve as an information radiator for a development environment. Display information from Jira, continuous
    integration systems and display time-based notifications for events such as daily standup.
</p>

<h1>
    Features
</h1>
<ul>
    <li>
        <b>Monitor Jenkins</b> - poll multiple Jenkins views/instances/jobs, useful in a large
        development environment.
    </li>
    <li>
        <b>LED strip</b> - ability to have a ws21x LED strip display patterns to indicate the build status,
        as well as standup and other events which can be customised.
    </li>
    <li>
        <b>Dashboards</b> - display a series of dashboards, which can each display either a
        web-page or a Jira wallboard.
    </li>
    <li>
        <b>Notifications</b> - display time-based notifications, which show an on-screen message and can change
        the LED strip to a defined pattern. Useful to notify the team of important events, such as daily standups.
    </li>
    <li>
        <b>Automated deployment</b> - using Ansible, updates and changes to configuration can be rolled-out
        within a single commnad to multiple build TVs. This also means new build TVs are fast to setup. Useful in a
        large development environment.
    </li>
    <li>
        <b>Modular architecture</b> - everything front-end is a client, with backend processes split into
        daemons by function, both sharing common libraries.
    </li>
    <li>
        <b>APIs</b> - all daemons expose their REST APIs, used for inter-process communication, through a remote daemon.
        The remote daemon is able to call an external service, such as your own website, with a randomly-generated token
        on startup. This means your own applications/services can be built on top of the build TV, and even reside
        on a remote machine, to provide remote control.
    </li>
    <li>
        <b>Stats</b> - a remote daemon service, running on each build TV, can report back statistics on its
        environment.
    </li>
</ul>

<h1>
    Download, Documentation &amp; Setup
</h1>
<p>
    The code, along with markup documentation, can be found at:
</p>
<p>
    <a href="https://github.com/limpygnome/rpi-misc">
        https://github.com/limpygnome/rpi-misc
    </a>
</p>

<h1>
    Gallery
</h1>

<h1>

</h1>