package com.limpygnome.controllers;

import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
    "/me",
    "/cv",
    "/contact"
})
public class StaticPages extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Fetch URL, this will form basis of template used. / is replaced with
        // _ i.e. test/page becomes test_page.ftl
        String urlPart = request.getRequestURI();
        if(urlPart == null)
            response.sendError(404);
        else if(urlPart.startsWith("/") && urlPart.length() > 1) {
            urlPart = urlPart.substring(1);
        }
        urlPart = urlPart.replace("/", "_");

        // Set template
        templateSettings.setTemplatePageContent(urlPart);
        
        // Set title
        String title;
        switch(urlPart)
        {
            case "me":          title = "Me";                       break;
            case "cv":          title = "Curriculum Vitae";         break;
            case "contact":     title = "Contact";                  break;
                
            default:    title = null;                               break;
        }
        
        if(title != null)
            templateSettings.setTemplatePageTitle(title);
    }
    
}
