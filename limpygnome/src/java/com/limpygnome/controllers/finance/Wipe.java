package com.limpygnome.controllers.finance;

import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
public class Wipe extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Check for postback
        String confirm = request.getParameter("confirm");
        if(confirm != null && confirm.equals("1"))
        {
            // Wipe all data
        }
        
        // Setup page
    }
    
}