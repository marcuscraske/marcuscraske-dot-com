package com.limpygnome.website.module.special;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpecialPageController
{

    @RequestMapping("/error")
    public String error()
    {
        return "pages/special/error";
    }

    @RequestMapping("/page-not-found")
    public String pageNotFound()
    {
        return "pages/special/page-not-found";
    }

}
