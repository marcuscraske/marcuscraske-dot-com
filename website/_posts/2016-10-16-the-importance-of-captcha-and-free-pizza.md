---
layout: post
title: The Importance of Captcha & Free Pizza!
selected: blog
---

<a href="/assets/posts/2016-10-16-importance-of-captcha/thumb.png">
    <img src="/assets/posts/2016-10-16-importance-of-captcha-papa-rewards/thumb.png" alt="Splunk, AWS and Slack" class="post-thumb" />
</a>

During October 2016, Papa Johns held a competition to promote their Papa Rewards system, whereby users could
enter an e-mail and potentially win Papa Reward points, or receive a £10 discount.

## Rewards
Besides the £10 discount, the actual rewards were:
- 8 points for a free small side
- 12 points for a free large side
- 25 points for a free large pizza

...with no prior orders on an account needed, no need for other items on your order and free delivery. Rewarded
at...<i>random?</i>.

Sounds like free dinner for a month! :)

## Free Dinner
Initially you could enter a random e-mail without any sort of confirmation and eventually players would win. This was
soon rectified, after about a week, by requiring users to confirm their e-mail when entering excessive entries from
an IP address. And using the same Gmail e-mail with a filter added, by appending a suffix to the username e.g.
`limpygnome@gmail.com` turned into `limpygnome+whatever.com`, was also banned, along with many temporary e-mail
providers.

But not all temporary e-mail providers. And the process, including the e-mail confirmation page, did not present
any form of human verification, such as a captcha, or limit the volume of requests/entries.


And as a result, a bot could mine large pizzas instead:

<p class="center">
    <a href="/assets/posts/2016-10-16-importance-of-captcha/workers.png">
        <img src="/assets/posts/2016-10-16-importance-of-captcha-papa-rewards/workers-thumb.png" alt="Automated bots" />
    </a>
</p>

...theoretically :D.

Source code:
<https://github.com/limpygnome/papa-rewards-bot>

Nom nom nom...
