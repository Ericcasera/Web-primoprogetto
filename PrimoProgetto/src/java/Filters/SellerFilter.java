/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class SellerFilter implements Filter {
    
    private FilterConfig filterConfig = null;    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        
        if(session == null || session.getAttribute("role") == null)
        {
            request.getRequestDispatcher("ErrorPage.html").forward(req, res);
            //Htmlmanager con messaggio "devi loggarti per potere entrare qui , sarai ridirezionato al login
        }
        else
        {
            String role = session.getAttribute("role").toString();
            int x = Integer.parseInt(role);
            if(x != 2)
            {
            request.getRequestDispatcher("ErrorPage.html").forward(req, res);
             //Htmlmanager con messaggio "sei autenticato come seller:per potere entrare qui dei essere un buyer, sarai ridirezionato alla home dei seller
            }
            else {
                chain.doFilter(request, response);
            }      
        }}

    @Override
    public void destroy() {
        
    }
   
    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
    }
}
