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

Simple Android and iOS app to allow users to find hygiene ratings of establishments (restaurants, bars, shops etc).

Download it from:
- [Google Play - TODO](todo)
- [iTunes App Store - TODO](todo)

## Reasons & Purpose
Similar existing apps had poor UI and didn't work well. I was also interested in the average rating for the
largest chains in the UK. Around the same time, I had an interest in learning more about MongoDB and React Native, thus
this seemed like a good fit!

## Backend
MongoDB is used to store information about each establishment: location, hygiene ratings, last inspection etc. The
main reason for using Mongo was that I needed a high-performant persistence store for geospatial queries.

This is fronted by Spring Boot to provide an API layer:
- __Geospatial queries:__ the user's location is sent, along with any search criteria, which is converted into a Mongo
  query using the Spring Data framework. Nearby establishments are sorted by distance and sent back.
- __Geo-encoding:__ the user can set their location using GPS (by default), or provide free-flow text (such as a city or post-code). In
  the latter, the text is geo-encoded using a third-party service and sent back. Requests to the third-party are cached
  using Spring Cache, with LRU (least recently used) storage.
- __Telemetry:__ data is sent back by the mobile app: purchases made, purchases restored and in-app errors.
- __Up-to-date data:__ the persistence store is updated every day, using open data from the Food Standards agency.

## Frontend
screenshots

mention naming, bundl name, package name for app store optimisation (ASO)
