package com.limpygnome.controllers.auth;

import com.limpygnome.servlet.Auth;
import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/auth/logout"})
public class Logout extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(Auth.isValid(this))
        {
            Auth.logout(this);
            templateSettings.setTemplatePageContent("auth/#logout");
        }
    }
    
}
