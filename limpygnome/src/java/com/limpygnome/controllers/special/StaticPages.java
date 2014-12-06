package com.limpygnome.controllers.special;

import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
    "/me",
    "/me/cycling",
    "/cv",
    "/contact",
    "/tools",
    "/music",
    "/software/binary_clock"
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

        // Set template
        templateSettings.setTemplatePageContent(urlPart);
    }
    
}
