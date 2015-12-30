package com.limpygnome.website.module.cv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CvController
{

    @RequestMapping("/cv")
    public String cv()
    {
        return "cv";
    }

}
