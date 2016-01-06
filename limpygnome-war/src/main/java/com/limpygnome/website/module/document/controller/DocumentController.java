package com.limpygnome.website.module.document.controller;

import com.limpygnome.website.common.constant.ViewConstants;
import com.limpygnome.website.common.controller.BaseController;
import com.limpygnome.website.module.document.model.Document;
import com.limpygnome.website.module.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.View;

@Controller
public class DocumentController extends BaseController
{
    private static final String DEFAULT_AUTHOR = "limpygnome";

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

        // Determine view name
        String viewName;

        if (fullUrl.startsWith("/") && fullUrl.length() > 0)
        {
            viewName = fullUrl.substring(1);
        }
        else
        {
            viewName = fullUrl;
        }

        // Build model-view
        ModelAndView modelAndView = createMV(viewName, document.getTitle());

        modelAndView.addObject("document", document);

        // -- Add meta data
        modelAndView.addObject(ViewConstants.META_AUTHOR, DEFAULT_AUTHOR);
        modelAndView.addObject(ViewConstants.META_DESCRIPTION, document.getDescription());
        modelAndView.addObject(ViewConstants.META_KEYWORDS, document.getKeywords());

        return modelAndView;
    }

}
