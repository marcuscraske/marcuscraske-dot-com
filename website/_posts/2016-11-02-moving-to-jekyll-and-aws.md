---
layout: post
title: Moving to Jekyll and AWS
category: blog
---

Recently I have transitioned my website to be statically generated, using Jekyll, and moved hosting to Amazon Web
Services (AWS). Not only does it load faster, due to being distributed around the world using AWS CloudFront, but it
but it's easier to maintain and reduces the potential to be attacked.

## Jekyll
The pages of this site are now either written in HTML, Markdown files or a combination of both. Previously I had used
FreeMarker, a Java templating engine to replace JSPs, which is slightly more powerful than what's used now. But this
power is not needed, since this site is just a collection of mainly static articles.

Since Java is no longer being used, there's no need to regularly update and maintain various dependencies, for bugs
and security. And since the site is statically generated, no real processing is occurring when viewing this site,
meaning content can be sent to the end-user a lot faster. And from a technical perspective, it's far less to maintain,
if anything at all, due to using far more agnostic and standardised markup languages.

## Amazon Web Services
One pain with my previous VPS was having to maintain Apache Tomcat, fronted by Apache HTTPD. Again, these had to be
regularly maintained for bugs and security, as well as: the operating system, system dependencies and bind (for DNS).
Not to mention the configuration for Apache heavily changing between some versions and requiring a re-write. And then
the version often differing between different distributions.

Some of the major advantages to using AWS:
- No longer need to maintain the underlying infrastructure or an application container.
- Content is distributed around the world using CloudFront as a *content distribution network* (CDN). Time taken to
  load site has drastically decreased when comparing old and new site using *around the world* testing tools.
- Auto-renewing SSL certificates, signed and provided by Amazon for **free**.
- Easily added automated monitoring of infrastructure through CloudWatch - alarms for cost, sudden spikes in traffic.

## Ansible
previously automated, but far less and easier now

## Other Things
general tidy up, cmpliance wcag/css/html, minification/optimization

## Summary

