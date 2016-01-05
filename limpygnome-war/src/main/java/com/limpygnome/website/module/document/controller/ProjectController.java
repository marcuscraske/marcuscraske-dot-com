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
public class ProjectController extends BaseController
{
    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping({ "/projects", "/projects/home" })
    public ModelAndView home()
    {
        return createMV("pages/projects/home", "projects");
    }

    @RequestMapping("/projects/{projectUrl}")
    public ModelAndView dynamicProject(@PathVariable("projectUrl") String projectUrl)
    {
        // Fetch document
        Document document = documentRepository.getProjectByUrl(projectUrl);

        if (document == null)
        {
            return forwardPageNotFound();
        }

        // Build model-view
        ModelAndView modelAndView = createMV("pages/projects/" + document.getUrl(), document.getTitle());
        modelAndView.addObject("document", document);
        return modelAndView;
    }

}
