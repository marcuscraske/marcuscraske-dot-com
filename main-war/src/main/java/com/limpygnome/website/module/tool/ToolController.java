package com.limpygnome.website.module.tool;

import com.limpygnome.website.common.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ToolController extends BaseController
{

    @RequestMapping({ "/tools", "/tools/home" })
    public ModelAndView home()
    {
        return createMV("pages/tools/home", "tools");
    }

}
