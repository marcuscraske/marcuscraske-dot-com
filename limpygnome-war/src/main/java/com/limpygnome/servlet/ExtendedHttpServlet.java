package com.limpygnome.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An extended servlet for serving content, using Freemarker as the render
 * engine, along with other features and improvements.
 * 
 * @author limpygnome
 */
public abstract class ExtendedHttpServlet extends HttpServlet
{
    // Constants
    // *************************************************************************
    private static final String         TEMPLATE_DATA_REQUEST_ATTRIB_KEY = "template_data";
    
    // Fields: View
    // *************************************************************************
    protected TemplateSettings          templateSettings;
    protected HashMap<String, Object>   templateData;
    
    // Fields: HTTP
    // *************************************************************************
    protected HttpServletRequest          request;
    protected HttpServletResponse         response;
    
    // Constructors
    // *************************************************************************
    public ExtendedHttpServlet()
    {
        this.templateSettings = new TemplateSettings();
        
        this.request = null;
        this.response = null;
    }
    
    // Methods
    // *************************************************************************
    public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    private void handleRequestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Setup fields
        this.request = request;
        this.response = response;
        
        // Fetch template data
        this.templateData = retrieveOrSetupRequestTemplateData(request);
        
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
    
    // Methods - Accessors
    // *************************************************************************
    public HashMap<String, Object> getTemplateData()
    {
        return templateData;
    }
    
    // Methods - Static
    // *************************************************************************
    
    /**
     * Retrieves or sets up the template data for the request.
     * 
     * @param request The current request.
     * @return Template data KV.
     */
    public static HashMap<String, Object> retrieveOrSetupRequestTemplateData(ServletRequest request)
    {
        // Check for existing data
        Object obj = request.getAttribute(TEMPLATE_DATA_REQUEST_ATTRIB_KEY);
        if(obj != null && obj instanceof HashMap)
        {
            return (HashMap<String, Object>) obj;
        }
        
        // Setup new data
        HashMap<String, Object> kv = new HashMap<>();
        request.setAttribute(TEMPLATE_DATA_REQUEST_ATTRIB_KEY, kv);
        return kv;
    }
}
