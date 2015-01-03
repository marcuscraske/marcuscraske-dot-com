package com.limpygnome.controllers.finance;

import com.limpygnome.ExtendedHttpServlet;
import com.limpygnome.finance.CsvStreamParser;
import com.limpygnome.providers.FinanceProvider;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/finance/upload"})
public class Upload extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String error = null;
        String result = null;
        
        // Check if files uploaded
        String requestContentType = request.getContentType();
        Collection<Part> parts;
        
        System.out.println("type - " + requestContentType);
        
        if(
            requestContentType != null && 
            requestContentType.toLowerCase().contains("multipart/form-data") &&
            !(parts = request.getParts()).isEmpty())
        {
            // Setup
            FinanceProvider provider = new FinanceProvider();
            provider.begin();
            
            StringBuilder rawResult = new StringBuilder();
            
            try
            {
                // Iterate each file and parse the data
                CsvStreamParser csp = new CsvStreamParser();
  
                int txs;
                for(Part p  : parts)
                {
                    try
                    {
                        txs = csp.parse(provider, null, p);
                        rawResult.append("Processed file '").append(p.getName()).append("', ").append(txs).append(" TXs parsed");
                    }
                    catch(Exception ex)
                    {
                        rawResult.append("Failed to parse file '").append(p.getName()).append("'");
                        throw ex;
                    }
                }
                
                // Persist all the new TXs
                provider.commit();
            }
            catch(Exception ex)
            {
                provider.rollback();
                Logger.getAnonymousLogger().log(Level.WARNING, "Failed to parse transaction data", ex);
            }
            finally
            {
                if(rawResult.length() > 0)
                {
                    result = rawResult.toString();
                }
                provider.close();
            }
        }
        
        // Setup page
        templateSettings.setTemplatePageContent("finance/upload");
        templateData.put("finance_result", result);
        templateData.put("finance_error", error);
    }
    
}
