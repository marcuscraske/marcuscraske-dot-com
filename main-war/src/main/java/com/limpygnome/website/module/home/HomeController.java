package com.limpygnome.website.module.home;

import com.limpygnome.website.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController
{

    @RequestMapping({ "/", "/home" })
    public ModelAndView home()
    {
        return createMV("pages/home/default-home", "welcome");
    }

}
