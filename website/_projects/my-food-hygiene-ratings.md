---
layout: post
title: My Food Hygiene Ratings
thumbnail: /assets/projects/my-food-hygiene-ratings/thumb.png
priority: 2019
selected: projects
---

<a href="/assets/projects/my-food-hygiene-ratings/thumb.png">
    <img alt="App icon" src="/assets/projects/my-food-hygiene-ratings/thumb.png" class="post-thumb" />
</a>

My Android and iOS app to allow users to find hygiene ratings of establishments (restaurants, bars, shops etc).

<p class="center">
    <a href="https://apps.apple.com/us/app/my-food-hygiene-ratings/id1455030045?ls=1">
        <img alt="Download on Apple Store" src="/assets/icons/apple-store-badge.png" class="app-store-badge" />
    </a>
    <a href="https://play.google.com/store/apps/details?id=com.danksloth.food.hygiene.ratings">
        <img alt="Download on Apple Store" src="/assets/icons/google-play-badge.png" class="app-store-badge" />
    </a>
</p>

## Reasons & Purpose
Similar existing apps had poor UI and didn't work well. I was also interested in the average rating for the
largest chains in the UK. Around the same time, I had an interest in learning more about MongoDB and React Native, thus
this seemed like a good fit.

## Backend
MongoDB is used to store information about each establishment: location, hygiene ratings, last inspection etc. The
main reason for using Mongo was that I needed a high-performant persistence store for geospatial queries.

Spring Boot is used to provide an API and backend for the mobile app:
- __Geospatial queries:__ the user's location is sent, along with any search criteria, which is converted into a Mongo
  query using the Spring Data framework. Nearby establishments are sorted by distance and sent back.
- __Geo-encoding:__ the existing geo-encoding data for establishments, provided by the Food Standards Agency, is
  inaccurate, thus addresses are geo-encoded using a third-party service. The user can set their location using
  GPS (by default), or provide free-flow text (such as a city or post-code). In the latter, the text is geo-encoded and
  requests are cached using Spring Cache, with LRU (least recently used) storage.
- __Push notifications:__ users can select their favourite establishments, and receive a notification on their device
  when an establishment inspection changes. This is done using Google Firebase.
- __Telemetry:__ data is sent back by the mobile app: purchases made, purchases restored and in-app errors.
- __Bulk processing:__ data is pulled from the [Food Standards Agency](https://www.food.gov.uk) website daily, with the
average rating for the top 100 largest chains calculated.

## Frontend
The mobile app is mostly written in JavaScript using the React Native framework, with state managed by Redux.

This allows writing logic and UI once, shared across both Android and iOS. A lot
of configuration is still required for each individual platform (SDKs/libraries,
build process, push-notifications, permissions etc).

Adverts are displayed to the user, with an in-app purchase available for _premium_ to remove them.

To see the latest screenshots: click the download buttons at the top of the page, which will show the app store listing.
