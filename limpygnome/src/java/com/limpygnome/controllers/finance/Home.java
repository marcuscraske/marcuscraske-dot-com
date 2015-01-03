package com.limpygnome.controllers.finance;

import com.limpygnome.ExtendedHttpServlet;
import com.limpygnome.models.FinanceAccount;
import com.limpygnome.models.FinanceTransaction;
import com.limpygnome.providers.FinanceProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/finance"})
public class Home extends ExtendedHttpServlet
{
    
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        FinanceProvider provider = new FinanceProvider();
        
        provider.begin();
        
        // Create account
        FinanceAccount acc = provider.accountFetch("test");
        if(acc == null)
        {
            acc = provider.accountCreate(new FinanceAccount("test"));
        }
        
        // Create tx
        FinanceTransaction tx = new FinanceTransaction(124, FinanceTransaction.TransactionType.Income, "test", null, 1200);
        tx.setAccount(acc);
        
        provider.transactionCreate(tx);
        
        templateSettings.setTemplatePageContent("finance/overview");
        templateSettings.setTemplatePageTitle("test");
        
        provider.commit();
        provider.close();
    }
}
