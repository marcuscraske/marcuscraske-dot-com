package com.limpygnome.providers;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author limpygnome
 */
public class BaseProvider
{
    private static final String DEFAULT_PERSISTENCE_UNIT = "limpygnome.com";
    
    protected EntityManager em;
    
    public BaseProvider()
    {
        em = Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT).createEntityManager();
    }
    
    public BaseProvider(String persistenceUnit)
    {
        em = Persistence.createEntityManagerFactory(persistenceUnit).createEntityManager();
    }
    
    public void begin()
    {
        em.getTransaction().begin();
    }
    
    public void commit()
    {
        em.getTransaction().commit();
    }
    
    public void rollback()
    {
        em.getTransaction().rollback();
    }
    
    public void close()
    {
        em.close();
        em = null;
    }
    
    public EntityManager getEntityManager()
    {
        return em;
    }
}
