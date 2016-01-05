package com.limpygnome.website.module.document.repository;

import com.limpygnome.website.module.document.model.Document;
import com.limpygnome.website.module.document.model.comparator.ArticleCreatedComparator;
import com.limpygnome.website.module.document.model.imp.Article;
import com.limpygnome.website.module.document.model.imp.Project;

import java.util.*;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepository
{
    private static List<Document> articles = new LinkedList<>();
    private static List<Document> projects = new LinkedList<>();

    /*
        Cache maps of URLs mapped to documents.
     */

    private static Map<String, Document> cachedMapArticles;
    private static Map<String, Document> cachedMapProjects;

    static
    {
        // Add articles
        addArticles();

        // Add projects
        addProjects();

        // Sort documents
        Collections.sort(articles, new ArticleCreatedComparator());
        Collections.sort(projects, new ArticleCreatedComparator());

        // Create URL maps
        cachedMapArticles = createUrlMap(articles);
        cachedMapProjects = createUrlMap(projects);
    }

    private static void addArticles()
    {
        articles.add(new Article(
                "Hacking the Nandos Pong Game",
                "thumbnail",
                "Exploiting a promotional game competition through a replay attack.",
                new LocalDate(2013, 9, 12),
                new LocalDate(2016, 1, 8),
                "hacking_nandos_pong_game"
        ));

        articles.add(new Article(
                "University",
                "thumbnail",
                "Overview of my time at university.",
                new LocalDate(2014, 6, 1),
                new LocalDate(2016, 1, 8),
                "university"
        ));
    }

    private static void addProjects()
    {
        projects.add(new Project(
                "Binary Clock",
                "thumbnail",
                "Version two of a binary clock electronics project, using a Raspberry Pi.",
                new LocalDate(2013, 9, 24),
                new LocalDate(2016, 1, 8),
                Project.Status.MAINTAINED,
                "binary_clock"
        ));

        projects.add(new Project(
                "Portable Postgres",
                "thumbnail",
                "Standalone portable application for managing a Postgres database server for development.",
                new LocalDate(2012, 9, 24),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "portable_postgres"
        ));

        projects.add(new Project(
                "PALS",
                "thumbnail",
                "Platform for automated and distributed marking of programming assessments.",
                new LocalDate(2014, 6, 20),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "pals"
        ));

        projects.add(new Project(
                "Build TV",
                "thumbnail",
                "Multiple daemons and clients used to create a functional build TV / information radiator.",
                new LocalDate(2014, 1, 12),
                new LocalDate(2016, 1, 12),
                Project.Status.MAINTAINED,
                "build_tv"
        ));
    }

    private static Map<String, Document> createUrlMap(List<Document> documents)
    {
        Map<String, Document> map = new HashMap<>(documents.size());

        for (Document document : documents)
        {
            if (map.containsKey(document.getUrl()))
            {
                throw new RuntimeException("Document with duplicate URL - " + document.getUrl());
            }

            map.put(document.getUrl(), document);
        }

        return map;
    }

    public List<Document> getArticles()
    {
        return articles;
    }

    public Article getArticleByUrl(String url)
    {
        return (Article) cachedMapArticles.get(url);
    }

    public List<Document> getProjects()
    {
        return projects;
    }

    public Project getProjectByUrl(String url)
    {
        return (Project) cachedMapProjects.get(url);
    }

}
