package com.limpygnome.controllers.special;

import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/404"})
public class Error404 extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Check if the page maps to an article
        //request.getRequestDispatcher("/home").forward(request, response);
        
        // Nothing found - output 404 page!
        templateSettings.setTemplatePageContent("404");
        templateSettings.setTemplatePageTitle("Page Not Found (404)");
    }
}
