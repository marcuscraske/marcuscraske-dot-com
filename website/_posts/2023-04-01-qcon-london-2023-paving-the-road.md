---
layout: post
title: "QCON London 2023: Paving the Road"
selected: blog
---

{% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/thumb.webp" alt="QCON logo" class="post-thumb" %}

My fifth year attending [QCON London](https://qconlondon.com/), a conference focused on knowledge-sharing high-level
emerging trends and adopted best-practices in software engineering, featuring many world-leading organisations and
subject-matter experts. And this year did not disappoint, with the emergence of _paved roads_.


## Paved Roads
<p class="center">
    {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/paved-road.webp" alt="Paved road, as an analogy" %}
</p>

Last year, we saw a few talks introduce _golden path_, referring to a well known Spotify Engineering blog post on
[golden paths to solve fragmentation in our software ecosystem](https://engineering.atspotify.com/2020/08/how-we-use-golden-paths-to-solve-fragmentation-in-our-software-ecosystem/).


As the breadth and complexity of software continues to grow, it becomes difficult to scale teams and productivity.
Often, there's a lot of fragmented processes, which are not well-documented, nor optimal, relying on rumour-driven
development (word of mouth). Teams are often figuring-out and performing a lot of the same manual steps
for e.g. setting up environments, deploying code and building new software; whilst wasting time figuring out said
processes and steps, and often not doing best practices and, even worse, delivering significantly less value to
customers.

And despite this year having a dedicated track, called _Paving the Road_, many talks outside the track referenced
the topic, perhaps suggesting this is a strong emerging topic that's now on the rise to mainstream industry adoption.

In particular, a few companies have already heavily adopted paved roads, with companies such as: Monzo, BBC, Picnic,
Snyk and SkyScanner; discussing the topic this year.

And one thing they all have in common is the topology of their teams:
- __Platform engineering teams:__
    - Focused on delivering and running infrastructure, such as running Kubernetes clusters and persistence.
- __Product teams:__
    - Focused on primarily writing business logic and delivering to customers.
- __Dev enablement, or dev experience, teams:__
    - Focused on abstracting the complexity of platform engineering, for adopting by product teams.
      Building skeleton services, optimising processes, building internal tooling and writing guides.


### How to Apply a Product Mindset to Your Platform Team Tomorrow
A product manager at Picnic gave a talk on running platform engineering as product, despite the customer being internal.

Key points:
- Talk to your customers (product teams) before building anything.
- Talk to the wider business (CTO/CPO, tech leads, product managers, teams etc) to check what is being built aligns with
  the companies' strategy.
- Design your (end-user) personas.
- In contract to the other companies, he suggested to not use [DORA metrics](https://cloud.google.com/blog/products/devops-sre/using-the-four-keys-to-measure-your-devops-performance),
  but rather think about the problem being solved, and directly measuring it.
- And internal marketing is important:
  - Sharing success stories (all hands, Slack, encourage your users to share their stories).
  - Running workshops / presentations.
  - Give out laptop stickers!


### 5 Principles for Enablement with (Almost) Nothing to do with Building Tools
The BBC already has platform teams, and has recently begun migrating their teams to using an engineering delivery
enablement group (dev enablement / dev experience).

Their five key principles:
  - We share knowledge and make things which could be improved, visible
  - We build and foster communities and relationships
  - We respect other teams’ time and uniqueness
  - We radiate a sharing mindset through collaboration
  - We aim for long-term improvements

Key takeaways:
- Be on the ground with the teams, not in an ivory tower:
  - Started by advertising their availability to teams to help work with them.
  - Worked with a team to improve their pipeline, including pairing, and saw an increase in the number of deployments.
- Give teams information, rather than enforcing rules
  - An example was moving license compliance from blocking/breaking builds to a Github bot, which instead gave
    teams information.
  - Trust teams to do the right thing.
- Communicate with your users:
  - Providing guidance for teams: examples and guides
  - Running retrospectives with teams
  - Running guilds (communities of practice / CoPs) every two weeks
    - Along with a full day to do work for a guild / CoP, such as looking at new tools
- Product teams don't have much time to move tools and their workflow, unless it's valuable
- Inner open-source model:
  - Contributions are not expected as a norm
  - Accepting contributions from teams as a priority over other work
  - An effort to merge every PR, but not blindly
- Sharing success stories and shout-outs external to their team through
- Be somewhere in the middle with balancing autonomy and alignment:
  - The BBC went too far with autonomy, which has now swung back towards somewhere in the middle between autonomy and
    alignment.


### Breaking Free From End-to-End Testing
Discover shared how they moved away from end-to-end testing to PACT contract testing, in order to improve build
reliability and speed, especially as they started to scale. It was mentioned it was quite cumbersome to setup PACT for
each service, and thus they added everything to a shared pipeline.


### Effective & Efficient Observability with Open Telemetry
SkyScanner gave some insight into their use of Open Telemetry, from operating at large scale:
- 1.8 million spans per second.
- 90k+ traces per second

Key points:
- An enablement team maintains a _golden path_, with shared libraries and container images.
- Make golden path the path of least resistance.
  - Can configure best practices into the golden path / paved road.
- They only keep 4.5%~ of all traces: errors, slowness and a random percentage of the remainder.
- Use of probability sampling, and tail-based sampling (for capturing the entire trace), although more difficult to 
  configure.


### Platform Engineering: Where Do We Go From Here?
The head of platforms at Snyk gave an overview of their transformation journey over the last two years.

Key points:
- Build incentives for adoption, rather than mandates.
- They built a developer portal, using Spotify [Backstage](https://backstage.spotify.com/):
  - Guides and training
  - Self-service
  - Plugins for wider integration, such as OpenAPI service documentation.
- Build for the 99% of developers.
- Strongly opinionated platforms:
  - Extremely repeatable.
  - Best-practice default configuration.
  - Teams can contribute back to a central function, including documentation.
- As of mid-2022, platform engineering was still early in adoption, but reaching peak hype as a trend.
- Various predictions (see [gallery](#gallery) for picture of slide).


### Banking on Thousands of Microservices
Monzo were back yet again, with an update on their journey with thousands of microservices.

Being able to watch their story over time has been particularly interesting, and they've made good key decisions
early-on, such as [adopting Kubernetes in 2016](https://monzo.com/blog/2016/09/19/building-a-modern-bank-backend).

It appears since 2019, they've increased from around 1,500 to over 2,000 microservices as well - impressive!

Key points:
- They've scaled from tens of engineers and tens of services, to 300 engineers and over 2,000 services.
- They have a clear definition of their bounded contexts, allowing for team ownership of services to change over time.
- Product teams are not given much autonomy:
  - All of the code lives in a mono-repo.
  - The enablement team maintains a shared core library, used by every service.
    - Providing functions around RPC, Cassandra, locking, logging, metrics, queueing and more.
  - Services only use Golang, with product teams only able to add business logic.
  - Observability built into every service, using: Prometheus, Open Telemetry and Jaeger.
- Abstract the platform for engineers:
  - Product teams shouldn't need to know about Kubernetes.
  - Product teams shouldn't need to be experts in platforms (Kubernetes, Cassandra etc).
  - Abstract core patterns.
  - Otherwise engineers are not being leveraged to their maximum potential, solving the wrong problems.
  - They've been moving to EKS, and engineers shoudln't know or care.
- Every new product engineer goes through a "Backend Engineering 101" guide when they join.
- Sharing of success stories:
  - A Slack channel to share screenshots of graphs where optimisations have been made with e.g. cpu usage.



## Summary
_You're still reading? 😂_

In summary, the experience and knowledge shared from the above talks/companies suggests the paved road is
edging towards mainstream adoption.

If one was to build a paved road, there's a few things in common you can do:
- Organise your engineering unit into a set of distinct teams: platform, dev enablement and product.
- Build opinionated services aligned with the objectives of the company, rather than giving teams autonomy.
- Abstract the platform from product teams:
  - Leverage the most out of your product teams by having them focused on customer problems.
  - Provide skeletons and templating for building services fast.
  - Best practice configuration can be in the skeleton for new services.
  - Allows for having a common pattern to build, deploy and support services; familiar to all engineers.
  - Product teams can focus on business logic, rather than being experts in platform infrastructure.
    - They shouldn't care how the platform works, or where it runs.
  - Reduce overall cognitive load.
- Share knowledge and work with your product teams:
  - Build a developer portal
  - Write guides.
  - Run workshops and guilds/communities-of-practice (CoPs).
  - Advocate and run training sessions.
  - Internally open-source, and allow contributions from product teams.
- Share success stories and achievements, and market your work like a product:
  - Encourage customers to share their success stories.
  - Credit those who've helped outside the team.
  - Give out stickers with a logo/branding.


## Gallery
<ul class="gallery">
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-day2-keynote-radia-perlman.webp"
                              description="Radia Perlman, creator of spanning tree algorithm, opening day two with a keynote" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-effective-efficient-observability.webp"
                              description="Effective & Efficient Observability (SkyScanner)" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-effective-efficient-observability-2.webp"
                              description="Effective & Efficient Observability (SkyScanner): Make golden path the path of least resistance" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-end-to-end-testing.webp"
                              description="Breaking Free From End-to-End Testing (Discover)" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-monzo-banking-thousands-microservices.webp"
                              description="Banking on Thousands of Microservices (Monzo)" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-platform-quote.webp"
                              description="Quote on platform orchestration" %}
    </li>
    <li>
        {% include image.html path="/assets/posts/2023-04-01-qcon-london-2023/qcon-snyk-predictions.webp"
                              description="Predictions from Crystal Hirschorn (Snyk)" %}
    </li>
</ul>


## Raw Notes
You can find my personal raw notes [here](https://github.com/marcuscraske/files-marcuscraske-dot-com/blob/main/conference-notes/qcon-london-2023.txt),
although they might not mean much, and may be inaccurate.
