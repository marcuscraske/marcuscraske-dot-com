---
title: "QCON London 2026"
date: "2026-03-25"
thumbnail: "/blog/2026-03-25-qcon-london-2026/thumb.jpg"
---

My eighth year attending [QCON London](https://qconlondon.com/), a conference focused on knowledge-sharing high-level
emerging trends and best-practices in software engineering, featuring many world-leading organisations and
subject-matter experts, looking into the potential of the next 3-5 years ahead.

Artificial intelligence (AI) was a dominant topic across all the different speaker tracks, and present in almost every
single talk, approaching the very peak of inflated expectations in the
[Gartner hype cycle](https://en.wikipedia.org/wiki/Gartner_hype_cycle).

Netflix probably gave one of the only talks that didn't mention AI at all, and an audience member even gave kudos in
the Q&A at the end, with the entire audience in applause...

Anyways, here are the key themes that stood out...


## 1. AI is an amplifier, not a replacement
Despite some of the [mainstream fearmongering](https://news.ycombinator.com/item?id=39507480),
AI is not replacing software engineers. A common theme across many talks is that humans are crucial for making judgement
calls. AI struggles with judgement, context, and trade-offs.

Instead, AI accelerates the output of code from engineers - good or bad.

We saw some great day-to-day examples beyond just generating code...

__Upgrading dependencies/libraries, including deprecated invocations:__ Spotify demonstrated an AI agent called _[Honk](https://engineering.atspotify.com/2025/11/spotifys-background-coding-agent-part-1)_,
capable of upgrading dependencies and associated code through Slack or Github, but at scale across a monorepo,
capable of upgrading hundreds of microservices. They went from 1k PRs (pull-requests) in 3 months to 1k PRs in
10 days. Whilst the AI was tuned, they observed at times the AI could remove tests to make tests pass.

__Summarising logs and incidents:__
[Anthropic](https://www.anthropic.com/) (company behind Claude) demonstrated using AI to look across all the inputs from observability (logs and metrics)
to investigate production issues. And look across recent changes (deployments,
config changes, cron jobs, rollbacks etc) to determine a potential cause. However, the speaker made a great
point that correlation is not causation, and many root causes can exist (including process issues). Thus,
judgement from a human is still required to guide AI, and address many wider root causes to prevent a production
incident.


## 2. Standardisation is becoming critical
With amplification, it's critical the increased output is built using standardisation.

Every high-performing organisation described paved roads as being maturely adopted for a while to achieve
standardisation, as seen at [QCON 2023](../2023-04-01-qcon-london-2023-paving-the-road/).

This is applicable with and without AI.

This includes:
- Documentation and organisation alignment about standards and ways of working (SDLC)
- Templates for creating new services
- Common (platform) libraries (logging, RPC, metrics, etc.)
- Standardised CI/CD pipelines
- Reusable infrastructure

Why?

For many reasons, including:
- Reducing cognitive load for product engineers, and reducing the decisions for common foundations.
- Making systems easier to understand, by abstracting complexity and decisions already made.
- Preventing a diverse ecosystem of tech stacks, building and maintaining the effectively the same value in many
  separate silos.
- And enabling business value (product) to be shipped faster.

A great quote from Spotify:
> “A diverse codebase brings a diverse set of problems.”

If you want humans and AI to be effective, you need consistency.

And with AI, many talks mentioned about using organisation-wide standardised agent and context files ([Claude](https://code.claude.com/docs/en/overview#customize-with-instructions-skills-and-hooks),
[Github Copilot](https://docs.github.com/en/copilot/how-tos/configure-custom-instructions/add-repository-instructions))
and internal documentation to provide guidance to the AI itself - including how to use internal (platform) libraries.


## 3. The real bottleneck is code reviews
And with the increased output, a several talks independently landed on the same conclusion:

> Code creation is no longer the bottleneck.

The new bottleneck is code reviews, and validating test correctness.

Whilst AI can generate tests, Spotify even saw AI agents remove failing tests to make the tests pass. Thus, human
review is still required. And they were able to rapidly increase the number of pull requests, as mentioned above, only
for it to become the bottleneck. The same was seen by Google Cloud industry-wide, from [DORA for AI](https://dora.dev/research/2025/dora-report/).

Another highlighted that:

> “When we automate a system, we leave the hardest parts to humans.”

We’re moving from _"how do we write code faster?"_ to _"how do we validate and integrate it safely?"_

Thus, AI accelerates code output - but arguably not true net productivity, as effort shifts into review and validation.


## 4. AI adoption can't be built on hope
A lot of organisations are making AI tooling available, but not providing the proper training and guidance for to be
mastered and used effectively. In fact, it can turn into vibe coding, and amplifying technical debt.

[Krys Flores](https://qconlondon.com/presentation/mar2026/copilots-orchestrators-12-week-playbook-training-engineering-teams-using-ai)
went through her experience of launching a training programme at her organisation over 12 weeks.

- Ran async through videos and materials, shared through Github.
  - Engineers were across timezones, and could train 1-2 hours a week when it was best for them.
- Exercises such as:
  - [Context engineering](https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents)
    - AI effectiveness depends heavily on the quality of context provided: standards, documentation, and constraints.
    - It's becoming a key discipline when using AI.
  - Pair programming, where: one person is prompting, and the other person is watching and helping to guide how the
    other person is thinking.
- Days where AI was turned-off towards the end, as to highlight where AI was really not needed,
  and were masking knowledge gaps.

She also iterated human judgement is critical.


## 5. AI costs are increasing
The honeymoon period with AI costs is coming to an end, with many providers shifting from monthly subscriptions
to usage based billing, or engineers hitting the monthly usage much quicker than before.

And as AI moves to orchestration loops, and the usage increases, so do the costs. And this is compounded further as
AI is adopted in other use cases, such as pull requests and observability.

So whilst AI itself may increase output, it doesn't necessarily increase productivity (as mentioned previously), but
nor does it reduce costs.

It's important AI is not just enabled, but optimised and used with intention.


## 6. AI may impact junior progression
One of the more subtle, but important themes, was how AI changes learning.

AI can remove a lot of the _mundane_ parts of engineering, such as:
- Writing boilerplate
- Debugging step-by-step
- Searching through documentation
- Exploring unfamiliar systems

While this increases speed, it also changes how engineers build intuition.

Some observations raised:
- Engineers spend less time deeply understanding code
- AI can mask gaps in knowledge
- Fewer "battle scars" from debugging and failure
- Over-reliance on generated solutions

There was also mention of studies showing:
- Perceived productivity increases
- But no statistically significant improvement in actual outcomes
- And reduced understanding of the code produced

A key insight:

> AI benefits experienced engineers the most.

Senior engineers:
- Can guide AI effectively
- Can spot incorrect outputs
- Understand trade-offs

Whereas junior engineers:
- May struggle to evaluate correctness
- May accept outputs without full understanding
- Risk slower long-term skill development

Some teams are actively counteracting this by:
- Running exercises without AI
- Encouraging deliberate practice
- Focusing on fundamentals alongside AI usage

The implication isn’t to avoid AI, but to be conscious of its impact.

> AI changes how engineers learn, and we need to design for that.

That likely means:
- Investing more in mentoring
- Emphasising first principles
- Ensuring engineers still understand the systems they work on

Otherwise, we risk accelerating output today at the cost of capability tomorrow.


## 7. Architecture must optimise for cognitive load
The Netflix and Nubank talks made this very clear:

> Systems don’t fail because they can’t scale.  
> They fail because humans can’t understand them.

Key takeaways:

- Service boundaries are less important than domain ownership
- Too many layers increase mental overhead
- Complexity creates stress, especially for engineers doing on-call
- Architecture must evolve with the business

When cognitive load becomes the bottleneck, the architecture has to change.


## 8. Governance should enable, not block
Sarah Wells gave a great talk on governance, with a strong message:

> Good governance is not about saying "no" - it’s about helping people say "yes" safely.

Effective governance includes:

- Clear standards (not vague policies)
- Guardrails embedded in tooling
- Automated checks
- Visibility of services and ownership

Ineffective governance includes:
- Documents nobody reads
- Manual approval processes
- Lack of visibility

Also:
- Shadow IT and shadow AI are signals - not just risks.

She shared about her time at the Financial Times, and how they had a _Technology Governance Group_, an open
forum to discuss changes with wide impact across engineering teams. Anyone could circulate a proposal before a
meeting, the meeting is used just for knowledge sharing and recording the decision, with a few people making the final
decision as to when it's safe to proceed.

She also mentioned how sometimes there's an allure to new technology. However, embracing old boring technology, where
the failure points are well understood, and letting others go through the pain is usually a better choice - unless it
would give the business a cutting-edge advantage. Don't let enthusiasm drive adoption from a few zealots.

She also gave a shout-out to Dan McKinley's post on
_[Choose Boring Technology](https://mcfunley.com/choose-boring-technology)_, worth a read.


## 9. Testing microservices at huge scale
[Suhail Patel](https://qconlondon.com/speakers/suhailpatel) has given many insightful and practical talks at QCON about
the platform engineering journey at Monzo. In this year's talk, he briefly covered how Monzo are testing at scale with
thousands of microservices.

In summary:
- Everything is deployed into a shared staging environment, and integrated together.
- Services even integrate with external test environments from other third-parties, such as Mastercard.
- When a service has changes, it can be tested in isolation by:
    - Deploying a separate instance in a new _tenancy_.
    - A HTTP header is used in a request at the edge of the system indicating which _tenancy_ to use.
    - When a request reaches a system using an affected service, it pivots to using the _tenancy_ instance of said
      service instead.
    - Data would also be partitioned for said tenancy.
    - E2E tests could be ran against the environment.
    - The instance and data could be torn down once finished.
- Thus, testing with real end-to-end integrations, rather than mocks, with parallel development.
- And these tests were also ran for each pull request.


## 10. Staff+ engineers shape systems
[Vanessa Formicola](https://qconlondon.com/presentation/mar2026/socio-technical-staff-engineer-architecture-culture-and-organisational-change)
gave a great analogy about how staff+ engineers are building the greenhouse (the environment)
for plants to thrive, and not just potting the plants inside it.

> You’re not just building systems - you’re building the conditions for systems to succeed.
> Build the greenhouse, not just the plants.

This includes using human judgement to:
- Understand the organisation, and what technology works best - not necessarily just good technology, as it may not be
  a good fit for the constraints of an organisation.
- Make value-based trade-offs.
- Redefining the frame/question i.e. people will often ask something, but actually mean something else.

And coaching and uplifting other engineers.


## Final thoughts
_Condensing the above even further_, the main takeaways for me were:

- AI amplifies existing systems and processes - it doesn’t fix them, and it can make existing problems even bigger.
- Standardisation is a critical prerequisite for speed.
- AI adoption must be supported and grown to succeed.
- Code review and validation are the new bottlenecks.
- The cost of AI is increasing, the honeymoon is over.
- AI changes how engineers learn, and we need to design for that.
- Cognitive load is an architectural concern.
- Choose boring technology, unless (rarely) it gives the business a cutting-edge.
- Human judgement is becoming the most valuable skill, in a world of AI.

The future of engineering isn’t just writing code faster. It’s building better systems for writing, validating, and
evolving code - together with AI as a force multiplier.


## Raw Notes
As usual, my raw notes from the conference with a lot more in-depth detail can be found here:
- <https://github.com/marcuscraske/files-marcuscraske-dot-com/tree/main/conference-notes>

The recordings of most talks are also released a few months later on InfoQ:
- <https://www.infoq.com/qcon/presentations/>
