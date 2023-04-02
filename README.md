# marcuscraske.com

<a href="LICENSE">
    <img src="https://img.shields.io/badge/license-MIT-blue.svg" alt="" />
</a>

This repository hosts the source code behind my personal website:

<https://marcuscraske.com>

Notable directories:
- *asset-sources* - the source files behind assets
- *deployment* - configuration for deploying the website.
- *docs* - the compiled site, for Github pages.


## Updating Site
Use `./run.sh` to make local changes.

Then `./build.sh` to do a clean build, and push up changes to publish the site.


## Jekyll
This is a simple Jekyll website, thus refer to the
[official docs](https://jekyllrb.com/docs/).

### Prerequisites
Linux:

````
sudo apt-get install ruby ruby-dev
````

Linux/MacOS:
````
sudo gem install bundler jekyll
````

Run from current directory:

````
bundle install
````

Install Node package manager, as per official instructions.

And with a terminal in this directory:
````
npm install
````

This will pull down a few third-party modules.
