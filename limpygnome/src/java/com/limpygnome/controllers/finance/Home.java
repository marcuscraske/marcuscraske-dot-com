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
     
        FinanceStatsOverview overview;
        FinanceStatsMonthly[] months;
        
        // Fetch stats
        try
        {
            overview = provider.statsOverviewFetch();
            months = provider.statsMonthlyFetch(6);
        }
        catch(Exception ex)
        {
            throw ex;
        }
        finally
        {
            provider.close();
        }
        
        // Build monthly stats
        StringBuilder monthsTotalIn = new StringBuilder();
        StringBuilder monthsTotalOut = new StringBuilder();
        StringBuilder monthsBalance = new StringBuilder();
        StringBuilder monthsLabel = new StringBuilder();
        
        String label;
        for(FinanceStatsMonthly month : months)
        {
            monthsTotalIn.append((double)month.getTotalIn()/100.0).append(",");
            monthsTotalOut.append(((double)month.getTotalOut()/100.0)*-1.0).append(",");
            monthsBalance.append(((double)month.getBalance()/100.0)).append(",");
            
            label = new DateTime(month.getYear(), month.getMonth(), 1, 0, 0, 0).toString("MMM yy");
            monthsLabel.append("\"").append(label).append("\",");
        }
        
        // -- Remove tailing comma
        if(
                monthsTotalIn.length() > 0 && monthsTotalOut.length() > 0 &&
                monthsBalance.length() > 0 && monthsLabel.length() > 0
            )
        {
            monthsTotalIn.deleteCharAt(monthsTotalIn.length()-1);
            monthsTotalOut.deleteCharAt(monthsTotalOut.length()-1);
            monthsBalance.deleteCharAt(monthsBalance.length()-1);
            monthsLabel.deleteCharAt(monthsLabel.length()-1);
        }
        
        // Setup page
        templateSettings.setTemplatePageContent("finance/overview");
        
        templateData.put("finance_months_labels", monthsLabel.toString());
        templateData.put("finance_months_totalin", monthsTotalIn.toString());
        templateData.put("finance_months_totalout", monthsTotalOut.toString());
        templateData.put("finance_months_balance", monthsBalance.toString());
    }
}
