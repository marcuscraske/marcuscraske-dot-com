package com.limpygnome.website.module.special;

import com.limpygnome.website.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteMapController extends BaseController
{

    @RequestMapping("/sitemap")
    public ModelAndView sitemap()
    {
        return createMV("pages/sitemap/home", "sitemap");
    }

}
