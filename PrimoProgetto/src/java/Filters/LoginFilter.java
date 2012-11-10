/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Database.HtmlManager;
import java.io.IOException;
import java.io.PrintWriter;
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
public class LoginFilter implements Filter {

    private FilterConfig filterConfig = null;
    
    public LoginFilter() {
    }    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
       
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        
        HttpSession session = req.getSession(false);
        
        if(session == null || session.getAttribute("role") == null)
        {
            chain.doFilter(request, response);
        }
        else
        {             
            String role = session.getAttribute("role").toString();
            int x = Integer.parseInt(role);           
            if(x == 1) {
                res.sendRedirect(req.getContextPath() + "/Buyer/BuyerHome");
            }
            else {
                res.sendRedirect(req.getContextPath() + "/Seller/SellerHome");
            }  
        }     
        }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
        
    }

}
