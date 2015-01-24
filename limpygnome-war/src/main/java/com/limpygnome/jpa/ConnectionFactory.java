package com.limpygnome.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Responsible for creating new connections.
 * 
 * @author limpygnome
 */
public class ConnectionFactory
{
    // Constants
    // *************************************************************************
    private static final String PERSISTENCE_UNIT_MAIN = "limpygnome.com";
    private static final String PERSISTENCE_UNIT_FINANCE = "finance.limpygnome.com";
    
    // Fields - Static
    // *************************************************************************
    private static ConnectionFactory instance = null;
    
    // Fields - Factories
    // *************************************************************************
    private EntityManagerFactory emfMain;
    private EntityManagerFactory emfFinance;
    
    // Constructors
    // *************************************************************************
    public ConnectionFactory()
    {
        emfMain = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_MAIN);
        emfFinance = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_FINANCE);
    }
    
    // Methods
    // *************************************************************************
    public synchronized EntityManager createMain()
    {
        return emfMain.createEntityManager();
    }
    
    public synchronized EntityManager createFinance()
    {
        return emfFinance.createEntityManager();
    }
    
    // Methods - Static
    // *************************************************************************
    public static synchronized ConnectionFactory getInstance()
    {
        if(instance == null)
        {
            instance = new ConnectionFactory();
        }
        return instance;
    }
}
