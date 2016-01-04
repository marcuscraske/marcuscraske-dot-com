package com.limpygnome.website.module.document.model;

import org.joda.time.LocalDate;

public abstract class Document
{
    private String title;
    private String thumbnailUrl;
    private String description;
    private LocalDate created;
    private LocalDate updated;

    public Document(String title, String thumbnailUrl, String description, LocalDate created, LocalDate updated)
    {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.created = created;
        this.updated = updated;
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

    public LocalDate getCreated()
    {
        return created;
    }

    public LocalDate getUpdated()
    {
        return updated;
    }

}
