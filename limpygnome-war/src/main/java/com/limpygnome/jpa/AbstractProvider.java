package com.limpygnome.jpa;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author limpygnome
 */
public abstract class AbstractProvider
{    
    protected EntityManager em;
    
    public AbstractProvider(EntityManager em)
    {
        this.em = em;
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
    
    public EntityManager getEntityManager()
    {
        return em;
    }
}