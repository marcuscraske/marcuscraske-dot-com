package com.limpygnome.website.common.controller;

import org.springframework.web.servlet.ModelAndView;

/**
 * Base controller with common functionality used by controllers.
 */
public abstract class BaseController
{

    public ModelAndView createMV(String page, String title)
    {
        ModelAndView modelAndView = new ModelAndView(page);
        modelAndView.addObject("pageTitle", title);
        return modelAndView;
    }

    public ModelAndView forwardPageNotFound()
    {
        return new ModelAndView("forward:/page-not-found");
    }

}
