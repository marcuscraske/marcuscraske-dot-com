package com.limpygnome.servlet;

import java.util.Random;

/**
 * CSRF / cross-site request forgery protection.
 * 
 * @author limpygnome
 */
public final class CSRF
{
    // Constants
    // *************************************************************************
    private static final int            CSRF_CODE_LENGTH = 32;
    private static final String         SESSION_KEY = "csrf";
    private static final String         FORM_ERROR_MESSAGE = "Invalid request, please try again.";
    
    // Constructors
    // *************************************************************************
    private CSRF() {}
    
    // Methods
    // *************************************************************************
    public static String generate(ExtendedHttpServlet servlet)
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
        
        // Store CSRF key into session
        if(servlet.request != null)
        {
            servlet.request.getSession().setAttribute("csrf", key);
        }
        
        return key;
    }
    
    /**
     * Checks if a form's CSRF value is correct. If the value is not correct,
     * the <i>error</i> template data is set with an appropriate error message.
     * 
     * @param servlet
     * @return 
     */
    public static boolean isValidForm(ExtendedHttpServlet servlet)
    {
        boolean valid = isValid(servlet);
        if(!valid)
        {
            servlet.templateData.put("error", FORM_ERROR_MESSAGE);
        }
        return valid;
    }
    
    public static boolean isValid(ExtendedHttpServlet servlet)
    {
        return isValid(servlet, "csrf");
    }
    
    public static boolean isValid(ExtendedHttpServlet servlet, String paramName)
    {
        if(servlet.request == null)
        {
            return false;
        }
        
        // Fetch and check param/form value
        String param = servlet.request.getParameter(paramName);
        if(param == null || param.length() != CSRF_CODE_LENGTH)
        {
            return false;
        }
        
        // Fetch and check CSRF value
        String sessCode = (String) servlet.request.getSession().getAttribute(SESSION_KEY);
        if(sessCode == null)
        {
            return false;
        }
        
        // Compare and reset
        boolean cmp = sessCode.equals(param);
        servlet.request.getSession().setAttribute(SESSION_KEY, null);
        return cmp;
    }
    
}
