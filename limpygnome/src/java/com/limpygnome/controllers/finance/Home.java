package com.limpygnome.controllers.finance;

import com.limpygnome.ExtendedHttpServlet;
import com.limpygnome.models.FinanceAccount;
import com.limpygnome.models.FinanceStatsMonthly;
import com.limpygnome.models.FinanceStatsOverview;
import com.limpygnome.models.FinanceTransaction;
import com.limpygnome.providers.FinanceProvider;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.joda.time.Months;

@WebServlet(urlPatterns = {"/finance"})
public class Home extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        FinanceProvider provider = new FinanceProvider();
        
        FinanceStatsMonthly[] months;
        FinanceAccount[] accounts;
        
        // Fetch data
        try
        {
            // Stats
            months = provider.statsMonthlyFetch(6, false);
            
            // Accounts
            accounts = provider.accountsFetch();
        }
        catch(Exception ex)
        {
            throw ex;
        }
        finally
        {
            provider.close();
        }
        
        // Build graph
        Utils.setupGraphBalance(this, months);
        
        // Setup page
        templateSettings.setTemplatePageContent("finance/overview");
        templateData.put("finance_accounts", accounts);
    }
}
