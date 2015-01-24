package com.limpygnome.controllers.finance;

import com.limpygnome.controllers.finance.misc.Utils;
import com.limpygnome.jpa.ConnectionFactory;
import com.limpygnome.servlet.ExtendedHttpServlet;
import com.limpygnome.jpa.models.FinanceStatsMonthly;
import com.limpygnome.jpa.models.FinanceStatsOverview;
import com.limpygnome.jpa.providers.FinanceProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
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
        EntityManager em = ConnectionFactory.getInstance().createFinance();
        
        try
        {
            // Fetch data
            FinanceProvider provider = new FinanceProvider(em);

            FinanceStatsMonthly[] months;
            FinanceStatsOverview overview;

            months = provider.statsMonthlyFetch(0, true);
            overview = provider.statsOverviewFetch();

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
            templateSettings.setTemplatePageContent("finance/#history");
            templateData.put("finance_months", months);
            templateData.put("finance_overview", overview);
        }
        finally
        {
            em.close();
        }   
    }
    
}
