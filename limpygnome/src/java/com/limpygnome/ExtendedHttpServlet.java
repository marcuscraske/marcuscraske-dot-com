package com.limpygnome;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Random;
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
    private static final int            CSRF_CODE_LENGTH = 32;
    private static final String         CSRF_SESSION_ATTRIB_NAME = "csrf";
    
    protected TemplateSettings          templateSettings;
    protected HashMap<String, Object>   templateData;
    
    private HttpServletRequest          req;
    private HttpServletResponse         resp;
    
    public ExtendedHttpServlet()
    {
        this.templateSettings = new TemplateSettings();
        this.templateData = new HashMap<>();
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
    
    public String csrfGenerate()
    {
        StringBuilder rawKey = new StringBuilder();
        Random rnd = new Random(System.currentTimeMillis());
        int charVal;
        for(int i = 0; i < CSRF_CODE_LENGTH; i++)
        {
            charVal = rnd.nextInt(25+25+10);
            
            // Alphabet char lower-case
            if(charVal < 25)
            {
                charVal += 97;
            }
            // Alphabet char upper-case
            else if(charVal >= 25 && charVal < 50)
            {
                charVal += 65;
            }
            // Numeric value
            else
            {
                charVal += 48;
            }
            
            rawKey.append((char)charVal);
        }
        
        String key = rawKey.toString();
        req.getSession().setAttribute("csrf", key);
        return key;
    }
    
    public boolean csrfIsValid()
    {
        return csrfIsValid("csrf");
    }
    
    public boolean csrfIsValid(String paramName)
    {
        // Fetch and check param/form value
        String param = req.getParameter(paramName);
        if(param == null || param.length() != CSRF_CODE_LENGTH)
        {
            return false;
        }
        
        // Fetch and check CSRF value
        String sessCode = (String)req.getSession().getAttribute(CSRF_SESSION_ATTRIB_NAME);
        if(sessCode == null)
        {
            return false;
        }
        
        // Compare and reset
        boolean cmp = sessCode.equals(param);
        req.getSession().setAttribute(CSRF_SESSION_ATTRIB_NAME, null);
        return cmp;
    }
    
    public HashMap<String, Object> getTemplateData()
    {
        return templateData;
    }
}
