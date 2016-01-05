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

    private static Map<String, Document> cachedMapByUrl;

    static
    {
        // Add articles
        addArticles();

        // Add projects
        addProjects();

        // Sort documents
        Collections.sort(articles, new ArticleCreatedComparator());
        Collections.sort(projects, new ArticleCreatedComparator());

        // Create map of URL <-> documents
        cachedMapByUrl = createUrlMap();
    }

    private static void addArticles()
    {
        articles.add(new Article(
                "Hacking the Nandos Pong Game",
                "thumbnail",
                "Exploiting a promotional game competition through a replay attack.",
                new LocalDate(2013, 9, 12),
                new LocalDate(2016, 1, 8),
                "/articles/hacking_nandos_pong_game"
        ));

        articles.add(new Article(
                "University",
                "thumbnail",
                "Overview of my time at university.",
                new LocalDate(2014, 6, 1),
                new LocalDate(2016, 1, 8),
                "/articles/university"
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
                "/projects/binary_clock"
        ));

        projects.add(new Project(
                "Portable Postgres",
                "thumbnail",
                "Standalone portable application for managing a Postgres database server for development.",
                new LocalDate(2012, 9, 24),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "/projects/portable_postgres"
        ));

        projects.add(new Project(
                "PALS",
                "thumbnail",
                "Platform for automated and distributed marking of programming assessments.",
                new LocalDate(2014, 6, 20),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "/projects/pals"
        ));

        projects.add(new Project(
                "Build TV",
                "thumbnail",
                "Multiple daemons and clients used to create a functional build TV / information radiator.",
                new LocalDate(2014, 1, 12),
                new LocalDate(2016, 1, 12),
                Project.Status.MAINTAINED,
                "/projects/build_tv"
        ));
    }

    private static Map<String, Document> createUrlMap()
    {
        Map<String, Document> map = new HashMap<>(articles.size() + projects.size());

        addToUrlMap(map, articles);
        addToUrlMap(map, projects);

        return map;
    }

    private static void addToUrlMap(Map<String, Document> map, List<Document> documents)
    {
        for (Document document : documents)
        {
            if (map.containsKey(document.getUrl()))
            {
                throw new RuntimeException("Document with duplicate URL - " + document.getUrl());
            }

            map.put(document.getUrl(), document);
        }
    }

    public List<Document> getArticles()
    {
        return articles;
    }

    public List<Document> getProjects()
    {
        return projects;
    }

    public Document getDocumentByUrl(String url)
    {
        return cachedMapByUrl.get(url);
    }

}
