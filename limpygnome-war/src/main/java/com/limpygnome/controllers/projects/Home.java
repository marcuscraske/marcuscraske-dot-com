package com.limpygnome.controllers.projects;

import com.limpygnome.jpa.ConnectionFactory;
import com.limpygnome.jpa.models.Project;
import com.limpygnome.jpa.providers.ProjectProvider;
import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Home for projects, lists all of the available project and status etc.
 * 
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/projects"})
public class Home extends ExtendedHttpServlet
{    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EntityManager em = ConnectionFactory.getInstance().createMain();
        
        try
        {
            // Fetch projects
            ProjectProvider provider = new ProjectProvider(em);
            Project[] projects = provider.projectsFetch();
            
            // Setup page
            templateData.put("projects", projects);
            templateSettings.setTemplatePageContent("#projects");
        }
        finally
        {
            em.close();
        }
        
    }   
    
}
