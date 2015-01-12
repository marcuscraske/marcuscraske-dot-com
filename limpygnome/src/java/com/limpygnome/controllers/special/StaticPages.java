package com.limpygnome.controllers.special;

import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/404", "/static_pages"})
public class StaticPages extends ExtendedHttpServlet
{
    private static final String PAGE_404 = "404";
    private static final String BASE_CLASSPATH_VIEWS = "/WEB-INF/classes/com/limpygnome/views/";
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String page = null;
        String origin = (String) request.getAttribute(OriginFilter.REQUEST_ATTRIBUTE_KEY_ORIGIN_URI);
        boolean verified = request.getAttribute(OriginFilter.REQUEST_ATTRIBUTE_KEY_VERIFIED) != null;

        // Check if to set a static page, else we're handling a 404...
        if(origin != null && verified)
        {
            page = origin;
        }

        // Set page
        templateSettings.setTemplatePageContent(page != null ? page : PAGE_404);
    }
    
    public static boolean isStaticPage(ServletContext ctx, String uri) throws MalformedURLException
    {
        // Check the page exists
        return ctx.getResource(BASE_CLASSPATH_VIEWS + uri + ".ftl") != null;
    }
}
