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
public class BuyerOrderFilter implements Filter {

    private String buyOrderRequestPattern , buyOrderConfirmPattern , buyOrderResponsePattern , redirectURL;
        
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest  req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        
        if(uri.equals(buyOrderRequestPattern))
            {
                try{
                    Integer.parseInt(req.getParameter("ID"));        
                   }catch (NumberFormatException ex){
                       res.sendRedirect(redirectURL);
                       return;
                   }
            }
        else if(uri.equals(buyOrderConfirmPattern))
            {           
                try{
                    Integer.parseInt(request.getParameter("number"));                    
                   }catch (NumberFormatException ex){
                       res.sendRedirect(redirectURL);
                       return;
                   }
                if(session.getAttribute("order") == null)
                    {
                       res.sendRedirect(redirectURL);
                       return;
                    }
            }
        else {
              if(session.getAttribute("order") == null)
                    {
                       res.sendRedirect(redirectURL);
                       return;
                    }        
            }
           
        
        chain.doFilter(request, response); 
        
    
    }
        
    
    @Override
    public void destroy() {
        
    }
   
    @Override
    public void init(FilterConfig filterConfig) {
        String contextPath = filterConfig.getServletContext().getContextPath();
        buyOrderRequestPattern  = contextPath + "/Buyer/BuyOrderRequest";
        buyOrderConfirmPattern  = contextPath + "/Buyer/BuyOrderConfirm";
        buyOrderResponsePattern = contextPath + "/Buyer/BuyOrderResponse";
        redirectURL = contextPath + "/Buyer/BuyerHome";
        
    }

}
