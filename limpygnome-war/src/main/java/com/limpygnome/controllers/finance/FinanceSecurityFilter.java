package com.limpygnome.controllers.finance;

import com.limpygnome.servlet.Auth;
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
 * A filter which performs the initial check the user is authorised. Each
 * individual page should also perform a check, but this acts as a
 * fail safe incase.
 * 
 * @author limpygnome
 */
@WebFilter(
    urlPatterns = {"/finance/*"},
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ERROR}
)
public class FinanceSecurityFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    
    @Override
    public void destroy() {}
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
    {
        if(!(req instanceof HttpServletRequest) || !Auth.isValid((HttpServletRequest) req))
        {
            // Redirect to 404...
            req.getRequestDispatcher("/404").forward(req, resp);
        }
        else
        {
            // Allow the request to continue...
            chain.doFilter(req, resp);
        }
    }
    
}
