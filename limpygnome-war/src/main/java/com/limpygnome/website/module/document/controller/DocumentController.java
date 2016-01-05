package com.limpygnome.website.module.document.controller;

import com.limpygnome.website.common.controller.BaseController;
import com.limpygnome.website.module.document.model.Document;
import com.limpygnome.website.module.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DocumentController extends BaseController
{
    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping({ "/projects", "/projects/home" })
    public ModelAndView projectsHome()
    {
        return createMV("pages/projects/home", "projects");
    }

    @RequestMapping({ "/articles", "/articles/home" })
    public ModelAndView articlesHome()
    {
        return createMV("pages/articles/home", "articles");
    }

    @RequestMapping("/projects/{path}")
    public ModelAndView projectDocument(@PathVariable("path") String path)
    {
        return dynamicDocument("/projects/" + path);
    }

    @RequestMapping("/articles/{path}")
    public ModelAndView articleDocument(@PathVariable("path") String path)
    {
        return dynamicDocument("/articles/" + path);
    }

    private ModelAndView dynamicDocument(String fullUrl)
    {
        // Fetch document
        Document document = documentRepository.getDocumentByUrl(fullUrl);

        if (document == null)
        {
            return forwardPageNotFound();
        }

        // Build model-view
        ModelAndView modelAndView = createMV("pages" + fullUrl, document.getTitle());
        modelAndView.addObject("document", document);
        return modelAndView;
    }

}
