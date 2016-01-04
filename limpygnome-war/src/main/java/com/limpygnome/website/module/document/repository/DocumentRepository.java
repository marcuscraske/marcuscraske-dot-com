package com.limpygnome.website.module.document.repository;

import com.limpygnome.website.module.document.model.Document;
import com.limpygnome.website.module.document.model.comparator.ArticleCreatedComparator;
import com.limpygnome.website.module.document.model.imp.Article;
import com.limpygnome.website.module.document.model.imp.Project;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepository
{
    private static List<Document> articles = new LinkedList<>();
    private static List<Document> projects = new LinkedList<>();

    static
    {
        // Add articles
        addArticles();

        // Add projects
        addProjects();

        // Sort documents
        Collections.sort(articles, new ArticleCreatedComparator());
        Collections.sort(projects, new ArticleCreatedComparator());
    }

    private static void addArticles()
    {
        articles.add(new Article(
                "Hacking the Nandos Pong Game",
                "thumbnail",
                "Exploiting a promotional game competition through a replay attack.",
                new LocalDate(2013, 9, 12),
                new LocalDate(2016, 1, 8)
        ));

        articles.add(new Article(
                "University",
                "thumbnail",
                "Overview of my time at university.",
                new LocalDate(2014, 6, 1),
                new LocalDate(2016, 1, 8)
        ));
    }

    private static void addProjects()
    {
        projects.add(new Project(
                "Binary Clock",
                "thumbnail",
                "Version two of a binary clock electronics project, using a Raspberry Pi.",
                new LocalDate(2013, 9, 24),
                new LocalDate(2016, 1, 8)
        ));
    }

    public List<Document> getArticles()
    {
        return articles;
    }

    public List<Document> getProjects()
    {
        return projects;
    }

}
