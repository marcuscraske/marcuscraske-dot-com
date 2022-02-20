---
layout: post
title: The Importance of Captcha & Free Pizza!
selected: blog
---

{% include image.html path="/assets/posts/2016-10-16-importance-of-captcha/thumb.png" alt="The Importance of Captcha & Free Pizza!" class="post-thumb" %}

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
At first, you could enter a random e-mail without any sort of confirmation and eventually players would win the main
prize (a large pizza).

After only a week, this was changed, requiring users to confirm their e-mail when entering excessive entries from
a single IP address. And using the same Gmail e-mail with a filter added, by appending a suffix to the username e.g.
`john@gmail.com` turned into `john+whatever@gmail.com`, was also banned, along with many temporary e-mail
providers.

But not all temporary e-mail providers. And the process, including the e-mail confirmation page, did not present
any form of human verification, such as a captcha, or limit the volume of requests/entries.


And as a result, a simple bot, using [Selenium](https://www.selenium.dev/documentation/), could _mine_ for a
specific number of large pizzas instead:

<p class="center">
    {% include image.html path="/assets/posts/2016-10-16-importance-of-captcha/workers.png" alt="Automated bots" class="post-thumb" %}
</p>

...theoretically :D.

Source code:
<https://github.com/limpygnome/papa-rewards-bot>

Nom nom nom...

__Legal disclaimer: this post only presents what could be possible, and is not an admission of guilt, or/and actions,
nor does it condone such behaviour. And any photos are artistic in nature, and this article was written and exists
only for the purposes of entertainment and education.__
