package com.limpygnome.servlet;

import com.limpygnome.jpa.models.AuthSession;
import javax.servlet.http.HttpServletRequest;

/**
 * Authentication functions.
 * 
 * @author limpygnome
 */
public final class Auth
{
    // Constants
    // *************************************************************************
    private static final String SESSION_KEY = "auth";
    
    // Constructors
    // *************************************************************************
    private Auth() {}
    
    // Methods - Static
    // *************************************************************************
    public static boolean isValid(ExtendedHttpServlet servlet)
    {
        return servlet != null ? isValid(servlet.request) : null;
    }
    
    public static boolean isValid(HttpServletRequest request)
    {
        return request != null && request.getSession().getAttribute(SESSION_KEY) != null;
    }
    
    public static boolean set(ExtendedHttpServlet servlet, String username)
    {
        if(servlet.request != null && username != null && username.length() > 0)
        {
            AuthSession sess = new AuthSession(username);
            servlet.request.getSession().setAttribute(SESSION_KEY, sess);
            return true;
        }
        return false;
    }
    
    public static void logout(ExtendedHttpServlet servlet)
    {
        if(servlet.request != null)
        {
            servlet.request.getSession().setAttribute(SESSION_KEY, null);
        }
    }
}
