package com.limpygnome;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
public abstract class ExtendedHttpServlet extends HttpServlet
{
    protected TemplateSettings          templateSettings;
    protected HashMap<String, Object>   templateData;
    
    public ExtendedHttpServlet()
    {
        this.templateSettings = new TemplateSettings();
        this.templateData = new HashMap<String, Object>();
    }
    
    public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    private void handleRequestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Log start time
        long start = System.currentTimeMillis();
        
        // Setup the request/response params
        response.setContentType("text/html;charset=UTF-8");
        
        // Get the controller to handle the request
        handleRequest(request, response);
        
        // Render template and output
        templateSettings.render(templateData, response);
        
        // Output process time to the end of the request
        long end = System.currentTimeMillis();
        long total = end-start;
        
        PrintWriter pw = response.getWriter();
        pw.append("<!-- Processed in ").append(String.valueOf(total)).append(" ms -->");
        pw.flush();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequestProcess(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        handleRequestProcess(req, resp);
    }
}
