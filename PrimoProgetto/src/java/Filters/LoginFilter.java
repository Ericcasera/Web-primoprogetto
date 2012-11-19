/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import java.io.IOException;
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
    
    private String buyerHomePattern , sellerHomePattern;
    
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
            int x = Integer.parseInt(session.getAttribute("role").toString());           
            if(x == 1) {
                res.sendRedirect(buyerHomePattern);
            }
            else {
                res.sendRedirect(sellerHomePattern);
            }  
        }     
        }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         String contextPath = filterConfig.getServletContext().getContextPath();
         buyerHomePattern   = contextPath + "/Buyer/BuyerController?op=home";
         sellerHomePattern  = contextPath + "/Seller/SellerController?op=home";
    }

    @Override
    public void destroy() {
        
    }

}
