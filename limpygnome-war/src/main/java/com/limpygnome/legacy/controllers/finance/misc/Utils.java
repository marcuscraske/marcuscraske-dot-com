package com.limpygnome.legacy.controllers.finance.misc;

import com.limpygnome.servlet.ExtendedHttpServlet;
import com.limpygnome.legacy.jpa.models.FinanceStatsMonthly;
import java.util.HashMap;
import org.joda.time.DateTime;

/**
 *
 * @author limpygnome
 */
public abstract class Utils
{
    public static void setupGraphBalance(ExtendedHttpServlet servlet, FinanceStatsMonthly[] months)
    {
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
        HashMap<String, Object> templateData = servlet.getTemplateData();
        
        templateData.put("finance_months_labels", monthsLabel.toString());
        templateData.put("finance_months_totalin", monthsTotalIn.toString());
        templateData.put("finance_months_totalout", monthsTotalOut.toString());
        templateData.put("finance_months_balance", monthsBalance.toString());
    }
    
}
