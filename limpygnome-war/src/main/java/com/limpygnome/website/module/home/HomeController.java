package com.limpygnome.website.module.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{

    @RequestMapping({ "/", "/home" })
    public String home()
    {
        return "pages/home/default-home";
    }

}
