package com.limpygnome.jpa.providers;

import com.limpygnome.jpa.AbstractProvider;
import com.limpygnome.jpa.models.Project;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author limpygnome
 */
public class ProjectProvider extends AbstractProvider
{
    public ProjectProvider(EntityManager em)
    {
        super(em);
    }
    
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
    
    public Project[] projectsFetch()
    {
        TypedQuery<Project> q = em.createQuery("SELECT p FROM Project p ORDER BY p.title ASC", Project.class);
        List<Project> result = q.getResultList();
        return result.toArray(new Project[result.size()]);
    }
    
}
