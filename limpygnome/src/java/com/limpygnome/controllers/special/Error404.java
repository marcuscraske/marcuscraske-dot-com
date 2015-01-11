package com.limpygnome.controllers.special;

import com.limpygnome.servlet.ExtendedHttpServlet;
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

        templateSettings.setTemplatePageContent("404");
    }
}
