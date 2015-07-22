package com.limpygnome.controllers.finance;

import com.limpygnome.controllers.finance.misc.Utils;
import com.limpygnome.jpa.ConnectionFactory;
import com.limpygnome.servlet.ExtendedHttpServlet;
import com.limpygnome.jpa.models.FinanceAccount;
import com.limpygnome.jpa.models.FinanceStatsMonthly;
import com.limpygnome.jpa.providers.FinanceProvider;
import com.limpygnome.servlet.Auth;
import java.io.IOException;
import javax.persistence.EntityManager;
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
        // Check the user is authorised
        if(!Auth.isValid(this))
        {
            return;
        }
        
        // Get instance of entity manager
        EntityManager em = ConnectionFactory.getInstance().createFinance();
        
        try
        {
            FinanceProvider provider = new FinanceProvider(em);

            FinanceStatsMonthly[] months;
            FinanceAccount[] accounts;

            // Fetch data
            // -- Stats
            months = provider.statsMonthlyFetch(6, false);

            // -- Accounts
            accounts = provider.accountsFetch();

            // Build graph
            Utils.setupGraphBalance(this, months);

            // Setup page
            templateSettings.setTemplatePageContent("finance/#overview");
            templateData.put("finance_accounts", accounts);
        }
        finally
        {
            em.close();
        }
    }
    
}
