/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Managers.DBmanager;
import Managers.HtmlManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class SellerServlet extends HttpServlet {

    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    private String contextPath , homePattern , addProductPattern , productsPattern, myStorePattern;
    private String redirectURL;
    
    @Override
    public void init() throws ServletException {
            this.DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            this.HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            this.contextPath = this.getServletContext().getContextPath();
            this.homePattern = "home";
            this.addProductPattern = "addProduct";
            this.productsPattern = "products";
            this.myStorePattern= "myStore";
            this.redirectURL = contextPath + "/Seller/SellerController?op=home";
    }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ArrayList category_list = DbManager.queryCategory(this.getServletContext());
        String id = session.getAttribute("user_id").toString();
        int user_id = Integer.parseInt(id);
        ArrayList sell_list = DbManager.querySell(this.getServletContext(), user_id);
        ArrayList sell_order_list = DbManager.querySellerOrders(this.getServletContext(), user_id);
        String op = request.getParameter("op");
        
        if(op.equals(homePattern)){
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();      
            try {
              HtmlManager.printSellerHomePage(out,  category_list, sell_order_list); 
            } finally {            
                out.close();
              }
        }
    
        else if(op.equals(productsPattern)){  
            ArrayList products_list = null;
            String message;
            int type;
            
            try{
              products_list = DbManager.queryProductsList(this.getServletContext(), Integer.parseInt(request.getParameter("category")));
            }
            catch (NumberFormatException e){
              response.sendRedirect(redirectURL);
              return;
             }
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();  
            try{     
                HtmlManager.printSellerProductsPage(out,  category_list, products_list, Integer.parseInt(request.getParameter("category")));             
            } finally {        
                out.close();
              }
        }
        
        else if(op.equals(addProductPattern)){     
            String message;
            int type;
            
            message = request.getParameter("message");
            
            try{
                type = Integer.parseInt(request.getParameter("type"));
                }
                catch (NumberFormatException e){ type=0;}    
            
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
            try{
                HtmlManager.printAddProductPage(out, category_list); 
        } finally {            
            out.close();
        }  }
        
        else if(op.equals(myStorePattern)){     
            String message;
            int type;
            
            message = request.getParameter("message");
            
            try{
                type = Integer.parseInt(request.getParameter("type"));
                }
                catch (NumberFormatException e){ type=0;}    
            
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
            try{
                HtmlManager.printMyStorePage(out, category_list, sell_list); 
        } finally {            
            out.close();
        }  }
        
        
    
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
