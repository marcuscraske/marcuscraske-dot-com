package com.limpygnome.website.module.document.controller;

import com.limpygnome.website.common.controller.BaseController;
import com.limpygnome.website.module.document.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ArticleController extends BaseController
{
    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping({ "/articles", "/articles/home" })
    public ModelAndView home()
    {
        return createMV("pages/articles/home", "articles");
    }

}
