package com.limpygnome.website.module.contact;

import com.limpygnome.website.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController extends BaseController
{

    @RequestMapping("/contact")
    public ModelAndView contact()
    {
        return createMV("pages/contact/default-contact", "contact");
    }

}
