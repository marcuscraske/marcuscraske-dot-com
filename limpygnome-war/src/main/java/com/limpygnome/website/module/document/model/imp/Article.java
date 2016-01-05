package com.limpygnome.website.module.document.model.imp;

import com.limpygnome.website.module.document.model.Document;
import org.joda.time.LocalDate;

public class Article extends Document
{

    public Article(String title, String thumbnailUrl, String description, LocalDate created, LocalDate updated,
                   String url)
    {
        super(title, thumbnailUrl, description, created, updated, url);
    }

}
