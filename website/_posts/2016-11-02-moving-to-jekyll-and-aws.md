---
layout: post
title: Moving to Jekyll and AWS
selected: blog
---

<a href="/assets/posts/2016-11-02-moving-to-jekyll-and-aws/thumb.png">
    <img alt="Jekyll logo" src="/assets/posts/2016-11-02-moving-to-jekyll-and-aws/thumb.png" class="post-thumb" />
</a>

Recently I have transitioned my website to be statically generated, using Jekyll, and moved hosting to Amazon Web
Services (AWS). Not only does it load faster, due to being distributed around the world using AWS CloudFront, but it
but it's easier to maintain and reduces the potential to be attacked. And costs less!

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
Even though my last site was automated through Ansible, far less has to be done now. Since Ansible its self
supports CloudFormation out of the box, I can just give it a json file and it'll create or update my infrastructure.

For uploading the site as static resources, to an S3 bucket, I use [s3cmd](https://s3tools.org). This is capable of
recursively checking for file changes, almost like rsync, and invalidating CloudFront for changed resources.

## Other Things
The site has been generally improved, as apart of this migration process:
- Resources are minified into a single file for each CSS and JavaScript.
- General resources such as fonts and images optimised. Just loading the home-page is equal to the same size of
  a single image on other sites. Since costs are utility based, it makes sense to cut-down on bandwidth.
- Use of font icons to support higher resolutions (vector-based / lossless).
- Site is accessibility friendly, following WCAG 2.0 AA friendly guidelines. It's important for everyone to be able to
  use the internet. And with HTML5 tags almost eliminating the need for any ARIA attributes, this does not require too
  much effort. The only real slight change was compromising on colours to have greater contrast and improving
  page structure. If you also happen to press tab when first loading the page, you'll also notice a red box. But the
  majority of non-accessibility users would never see this.

## Summary
Website is cheaper, less infrastructure maintenance, reduced complexity and attack vectors, site is easier to maintain
and extend. And got to do a few improvements.
