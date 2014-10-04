/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author limpygnome
 */
@WebFilter(filterName = "ArticlesFilter", urlPatterns = {"/*"})
public class ArticlesFilter implements Filter {
    
    private static final boolean debug = true;

    public ArticlesFilter()
    {
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException
    {
        
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        
        req.setAttribute("origin", req.getRequestURI());
        
        String url = req.getRequestURI().toString();
        
        System.out.println("req url: "+url);
        
        
        if(!url.startsWith("/hello_world"))
        {
            String dest = "/hello_world";
            request.getRequestDispatcher(dest).forward(request, response);
        }
        else
        {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {        
    }

    public void init(FilterConfig filterConfig)
    {
    }
}
