package com.limpygnome.providers;

import com.limpygnome.models.FinanceAccount;
import com.limpygnome.models.FinanceStatsMonthly;
import com.limpygnome.models.FinanceStatsOverview;
import com.limpygnome.models.FinanceTransaction;
import java.util.Collections;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class FinanceProvider extends BaseProvider
{

    public FinanceProvider()
    {
        super("finance.limpygnome.com");
    }
    
    public FinanceAccount[] accountsFetch()
    {
        TypedQuery<FinanceAccount> accounts = em.createQuery("SELECT fa FROM FinanceAccount fa", FinanceAccount.class);
        
        try
        {
            List<FinanceAccount> res = accounts.getResultList();
            return res.toArray(new FinanceAccount[res.size()]);
        }
        catch(NoResultException ex)
        {
            return new FinanceAccount[0];
        }
    }
    
    public FinanceAccount accountFetch(String sortCode, String accountNumber)
    {
        return accountFetch(
                FinanceAccount.convertUkToAccountIdentifier(sortCode, accountNumber)
        );
    }
    
    public FinanceAccount accountFetch(String accountIdentifier)
    {
        TypedQuery<FinanceAccount> q = em.createQuery("SELECT fa FROM FinanceAccount fa WHERE fa.accountIdentifier = :ai", FinanceAccount.class);
        q.setParameter("ai", accountIdentifier);
        try
        {
            return q.getSingleResult();
        }
        catch(NoResultException ex)
        {
            return null;
        }
        //return em.find(FinanceAccount.class, accountIdentifier);
    }
    
    public FinanceAccount accountCreate(FinanceAccount account)
    {
        em.persist(account);
        return account;
    }
    
    public void transactionCreate(FinanceTransaction tx)
    {
        em.persist(tx);
    }
    
    public FinanceStatsOverview statsOverviewFetch()
    {
        return em.find(FinanceStatsOverview.class, 1);
    }
    
    public FinanceStatsMonthly[] statsMonthlyFetch(int months, boolean reverse)
    {
        TypedQuery<FinanceStatsMonthly> q = em.createQuery("SELECT fsm FROM FinanceStatsMonthly fsm", FinanceStatsMonthly.class);
        
        if(months > 0)
        {
            q.setMaxResults(months);
        }
        
        try
        {
            List<FinanceStatsMonthly> result = q.getResultList();
            
            // Items come reversed from the db
            if(!reverse)
            {
                Collections.reverse(result);
            }
            
            return result.toArray(new FinanceStatsMonthly[result.size()]);
        }
        catch(NoResultException ex)
        {
            return new FinanceStatsMonthly[0];
        }
    }
}
