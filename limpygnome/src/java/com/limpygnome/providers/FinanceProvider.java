package com.limpygnome.providers;

import com.limpygnome.models.FinanceAccount;
import com.limpygnome.models.FinanceTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.hibernate.Criteria;

public class FinanceProvider extends BaseProvider
{
    public FinanceProvider()
    {
        super("limpygnome.comPU");
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
}
