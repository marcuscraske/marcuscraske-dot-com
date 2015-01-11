package com.limpygnome.controllers.finance;

import com.limpygnome.servlet.ExtendedHttpServlet;
import com.limpygnome.models.FinanceStatsMonthly;
import com.limpygnome.models.FinanceStatsOverview;
import com.limpygnome.providers.FinanceProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/finance/history"})
public class History extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        FinanceProvider provider = new FinanceProvider();
        
        // Fetch data
        FinanceStatsMonthly[] months;
        FinanceStatsOverview overview;
        
        try
        {
            months = provider.statsMonthlyFetch(0, true);
            overview = provider.statsOverviewFetch();
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
        // TODO: change to iterate items in reverse in util call, more efficient
        {
            List<FinanceStatsMonthly> monthsRev = new ArrayList<>();
            for(FinanceStatsMonthly month : months)
            {
                monthsRev.add(month);
            }
            Collections.reverse(monthsRev);
            FinanceStatsMonthly[] stats = monthsRev.toArray(new FinanceStatsMonthly[monthsRev.size()]);
            Utils.setupGraphBalance(this, stats);
        }
        // Setup page
        templateSettings.setTemplatePageContent("finance/history");
        templateData.put("finance_months", months);
        templateData.put("finance_overview", overview);
    }
    
}
