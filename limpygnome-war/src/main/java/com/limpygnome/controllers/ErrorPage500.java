package com.limpygnome.controllers;

import com.limpygnome.servlet.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles HTTP 500 (internal server error) response.
 * 
 * @author limpygnome
 */
@WebServlet(urlPatterns = {"/500"})
public class ErrorPage500 extends ExtendedHttpServlet
{
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO: perform auditing of request for security
        
        templateSettings.setTemplatePageContent("#500");
    }
    
}
