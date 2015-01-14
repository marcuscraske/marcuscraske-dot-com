package com.limpygnome.providers;

import com.limpygnome.models.Project;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author limpygnome
 */
public class ProjectProvider extends BaseProvider
{
    
    public Project projectCreate(Project project)
    {
        em.persist(project);
        return project;
    }
    
    public Project projectFetchByUrl(String urlPart)
    {
        TypedQuery<Project> q = em.createQuery("SELECT p FROM Project p WHERE p.urlPart = :url_part", Project.class);
        q.setParameter("url_part", urlPart);
        
        try
        {
            return q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
    }
    
}
