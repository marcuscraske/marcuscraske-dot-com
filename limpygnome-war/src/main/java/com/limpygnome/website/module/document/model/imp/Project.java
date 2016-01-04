package com.limpygnome.website.module.document.model.imp;

import com.limpygnome.website.module.document.model.Document;
import org.joda.time.LocalDate;

public class Project extends Document
{

    public enum Status
    {
        MAINTAINED,
        DISCONTINUED,
        EXPERIMENT
    }

    public Project(String title, String thumbnailUrl, String description, LocalDate created, LocalDate updated)
    {
        super(title, thumbnailUrl, description, created, updated);
    }

}
