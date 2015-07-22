package com.limpygnome.controllers;

import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/cv"})
public class CV extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        templateSettings.setTemplatePage("cv/#layout");
    }
    
}
