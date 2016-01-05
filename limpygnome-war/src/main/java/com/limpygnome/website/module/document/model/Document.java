package com.limpygnome.website.module.document.model;

import org.joda.time.LocalDate;

public abstract class Document
{
    private String title;
    private String thumbnailUrl;
    private String description;
    private String keywords;
    private LocalDate created;
    private LocalDate updated;
    private String url;

    public Document(String title, String thumbnailUrl, String description, String keywords, LocalDate created,
                    LocalDate updated, String url)
    {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.keywords = keywords;
        this.created = created;
        this.updated = updated;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public String getThumbnailUrl()
    {
        return thumbnailUrl;
    }

    public String getDescription()
    {
        return description;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public LocalDate getCreated()
    {
        return created;
    }

    public LocalDate getUpdated()
    {
        return updated;
    }

    public String getUrl()
    {
        return url;
    }

}
