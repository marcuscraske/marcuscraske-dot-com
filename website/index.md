---
layout: page
title: "welcome"
---

<section>

    {% for post in site.posts %}

        <article>
            <time datetime="{{ post.date | date: "%F" }}">
                {{ post.date | date:"%b %d, %Y" }}
            </time>
            <h1>
                <a href="{{ post.url }}">
                    {{ post.title }}
                </a>
            </h1>
            <p>
                {{ post.excerpt | remove: '<p>' | remove: '</p>' }} ...
            </p>
        </article>

    {% endfor %}

</section>
