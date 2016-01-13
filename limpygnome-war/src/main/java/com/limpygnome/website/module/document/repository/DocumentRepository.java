package com.limpygnome.website.module.document.repository;

import com.limpygnome.website.module.document.model.Document;
import com.limpygnome.website.module.document.model.comparator.DocumentCreatedComparator;
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
        Collections.sort(articles, new DocumentCreatedComparator());
        Collections.sort(projects, new DocumentCreatedComparator());

        // Create map of URL <-> documents
        cachedMapByUrl = createUrlMap();
    }

    private static void addArticles()
    {
        // target description - 120 chars:
        //      "                                                                                                                          "

        articles.add(new Article(
                "Hacking the Nandos Pong Game",
                "/content/articles/hacking-nandos-pong-game/thumb.png",
                "Exploiting a Nandos promotional game competition through a replay attack. Using Wireshark for sniffing and sending altered requests.",
                "hacking,nandos,wireshark,sniffing,replay attack",
                new LocalDate(2013, 9, 12),
                new LocalDate(2016, 1, 15),
                "/articles/hacking-nandos-pong-game"
        ));

        articles.add(new Article(
                "University",
                "/content/articles/university/thumb.png",
                "Overview of my time at university.",
                "university,limpygnome,uea",
                new LocalDate(2014, 6, 1),
                new LocalDate(2016, 1, 15),
                "/articles/university"
        ));

        articles.add(new Article(
                "Cycling",
                "/content/articles/cycling/eating-matters.png",
                "About my bikes and rides.",
                "cycling,riding,century,bikes,road bikes,personal,records,charity rides,gatorskins",
                new LocalDate(2014, 6, 1),
                new LocalDate(2016, 1, 15),
                "/articles/cycling"
        ));
    }

    private static void addProjects()
    {
        projects.add(new Project(
                "Binary Clock",
                "/content/projects/binary-clock/thumb.png",
                "Version two of a binary clock electronics project, using a Raspberry Pi. Written in C++ with a threaded web server front-end.",
                "binary,clock,raspberry pi,raspberry,pi,c++,shift,registers,tmp 36,mcp3008,gpio",
                new LocalDate(2013, 9, 24),
                new LocalDate(2016, 1, 8),
                Project.Status.MAINTAINED,
                "/projects/binary-clock"
        ));

        projects.add(new Project(
                "Portable Postgres",
                "/content/projects/portable-postgres/thumb.png",
                "Standalone portable application for managing a Postgres database server for development.",
                "portable,postgres,application,.net,c#",
                new LocalDate(2012, 9, 24),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "/projects/portable-postgres"
        ));

        projects.add(new Project(
                "PALS",
                "/content/projects/pals/thumb.png",
                "An open source platform for automated and distributed marking of programming assessments, completed as an undergraduate project.",
                "programming,assessment,automated,distributed,java,rmi,sql",
                new LocalDate(2014, 6, 20),
                new LocalDate(2016, 1, 8),
                Project.Status.DISCONTINUED,
                "/projects/pals"
        ));

        projects.add(new Project(
                "Build TV",
                "/content/projects/build-tv/thumb.png",
                "Multiple daemons and clients used to create a functional build TV / information radiator; integrating Jira, Jenkins and a ws21x LED strip.",
                "build,tv,raspberry pi,raspberry,pi,notifications,jenkins,jira,automated,ansible",
                new LocalDate(2016, 1, 12),
                new LocalDate(2016, 1, 12),
                Project.Status.ACTIVE,
                "/projects/build-tv"
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
