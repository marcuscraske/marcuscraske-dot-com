/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.limpygnome.controllers.special;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Web application lifecycle listener.
 *
 * @author limpygnome
 */
@WebFilter(
    urlPatterns = {"/*"},
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR}
)
public class OriginFilter implements Filter
{
    public static final String REQUEST_ATTRIBUTE_KEY_ORIGIN_URI = "origin";
    public static final String REQUEST_ATTRIBUTE_KEY_VERIFIED = "origin_verified";
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;
        
        // Only allow this filter to run once per an actual request
        if(request.getAttribute(REQUEST_ATTRIBUTE_KEY_ORIGIN_URI) == null)
        {
            String origin = request.getRequestURI();
            
            // Remove trailing slash
            if(origin.startsWith("/") && origin.length() > 1)
            {
                origin = origin.substring(1);
            }
            if(origin.endsWith("/") && origin.length() > 1)
            {
                origin = origin.substring(0, origin.length()-1);
            }
            
            // Set the origin
            request.setAttribute(REQUEST_ATTRIBUTE_KEY_ORIGIN_URI, origin);
            
            // Check if it exists as a static page
            if(StaticPages.isStaticPage(req.getServletContext(), origin))
            {
                // Set as verified
                request.setAttribute(REQUEST_ATTRIBUTE_KEY_VERIFIED, true);
                // Let static pages controller continue request
                request.getRequestDispatcher("/static_pages").forward(req, resp);
            }
        }
        
        chain.doFilter(req, resp);
    }

}
