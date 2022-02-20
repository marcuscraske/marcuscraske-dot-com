---
layout: post
title: Parrot Manager
selected: blog
---

{% include image.html path="/assets/posts/2017-04-19-parrot-manager/parrot-icon.png" alt="Parrot manager logo" class="post-thumb" %}

My side project for the last few months.

A simple cross-platform modern password manager, with the ability to synchronize a password database over SSH
and make automatic backups.

## Reasons &amp; Purpose
Many pre-existing password managers do provide the ability to synchronize over SSH very well, or at all, nor offer a
modern user interface. There are cloud services, such as [LastPass](https://www.lastpass.com/), but the thought of sticking passwords in a
large centralized target seems brave.

Instead I wanted an application which allowed storing my passwords on my own decentralized boxes, and happened to
want a new project to play with the new Angular.

## How It Works
It uses the following stack:
* Angular - modular JavaScript single page application for user interface. At the time of starting parrot, Angular
had just released v2 and were working on v4. This was probably quite risky, given the major changes compared to v1,
such as a heavily dependency on TypeScript and moving to everything being a component. This initially turned out to be
a slight learning curve, mainly unlearning the first version of AngularJS, which then turned into the ability to
quickly build many different screens, with complex behaviour.
* Bootstrap - responsive and reusable components.
* Java - provides portability and the heavy lifting, using [Bouncy Castle](https://www.bouncycastle.org/) for
cryptography.
  * JavaFX / Webkit - the AngularJS app is rendered through a webkit control, using the JavaFX framework. This
  communicates with the application through POJOs injected as JavaScript objects at a global scope. These POJOs are
  just services, which are wrapped within Angular services and used within components.

## Screenshots, Download &amp; Contribute
Want to give it a try or contribute?

<https://github.com/limpygnome/parrot-manager>

## Outcome
Small and fun project, learned a lot about creating a hybrid desktop application. And reaping the benefits of a
new password manager.
