/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Filters;

import Managers.HtmlManager;
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
public class BuyerFilter implements Filter {

    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
      
        HttpSession session = req.getSession(false);
        
        if(session == null || session.getAttribute("role") == null)
        {
             res.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();   
             HtmlManager.printErrorPage(out,"Index.jsp " ,req.getContextPath());
             out.close();
        }
        else
        {
            String role = session.getAttribute("role").toString();
            int x = Integer.parseInt(role);
            if(x != 1)
            {
             res.setContentType("text/html;charset=UTF-8");
             PrintWriter out = response.getWriter();   
             HtmlManager.printErrorPage(out,"Seller/SellerHome " ,req.getContextPath());
             out.close();
            }
            else {  
                chain.doFilter(request, response);
            }      
        }
    }
        
    
    @Override
    public void destroy() {
        
    }
   
    @Override
    public void init(FilterConfig filterConfig) {
    }

}
