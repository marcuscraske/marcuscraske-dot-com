package com.limpygnome.controllers.auth;

import com.limpygnome.servlet.Auth;
import com.limpygnome.servlet.CSRF;
import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/auth"})
public class Home extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Handle request based on authentication context
        if(Auth.isValid(this))
        {
            handleRequest_authenticated(request, response);
        }
        else
        {
            handleRequest_unauthenticated(request, response);
        }
    }
    
    public void handleRequest_authenticated(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
    {
        templateSettings.setTemplatePageContent("auth/#home");
    }
    
    
    public void handleRequest_unauthenticated(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException
    {
        // Check for postback
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if  (
                username != null && username.length() > 0 &&
                password != null && password.length() > 0 &&
                CSRF.isValidForm(this)
            )
        {
            // Locate hash for username
            
            // Hash password, check for match
            
            //Auth.set(this, username);
        }
        
        templateSettings.setTemplatePageContent("auth/#login");
    }
}
