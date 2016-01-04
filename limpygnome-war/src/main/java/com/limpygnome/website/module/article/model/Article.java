package com.limpygnome.website.module.article.model;

import org.joda.time.DateTime;

/**
 * Created by limpygnome on 04/01/16.
 */
public class Article
{
    private String title;
    private String thumbnailUrl;
    private String description;
    private DateTime created;
    private DateTime updated;

    public Article(String title, String thumbnailUrl, String description, DateTime created, DateTime updated)
    {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.created = created;
        this.updated = updated;
    }



}
