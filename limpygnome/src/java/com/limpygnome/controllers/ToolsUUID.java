package com.limpygnome.controllers;

import com.limpygnome.ExtendedHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/tools/uuid"})
public class ToolsUUID extends ExtendedHttpServlet
{

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Check if UUID generation requested
        
        // Setup template
        
    }
}
