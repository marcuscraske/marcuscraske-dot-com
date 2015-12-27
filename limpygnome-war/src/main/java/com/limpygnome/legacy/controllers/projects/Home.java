package com.limpygnome.legacy.controllers.projects;

import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Home for projects, lists all of the available project and status etc.
 * 
 * @author limpygnome
 */
//@WebServlet(urlPatterns = {"/projects"})
public class Home extends ExtendedHttpServlet
{    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        EntityManager em = ConnectionFactory.getInstance().createMain();
        
        try
        {
            // Fetch projects
//            ProjectProvider provider = new ProjectProvider(em);
//            Project[] projects = provider.projectsFetch();
            
            // Setup page
//            templateData.put("projects", projects);
            templateSettings.setTemplatePageContent("#projects");
        }
        finally
        {
//            em.close();
        }
        
    }   
    
}
