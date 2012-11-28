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
 * Servlet per la gestione di BuyerHome , Products , Orders
 */
public class BuyerServlet extends HttpServlet {

    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    private String contextPath , homePattern , ordersPattern , productsPattern;
    private String redirectURL;
    
    
    @Override
    public void init() throws ServletException {
            DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            contextPath = this.getServletContext().getContextPath();
            homePattern = "home";
            ordersPattern = "orders";
            productsPattern = "products";
            redirectURL = contextPath + "/Buyer/BuyerController?op=home";
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
         
        HttpSession session = request.getSession(false);
        ArrayList category_list = DbManager.queryCategory();
        String op = request.getParameter("op");
        
        if(op.equals(homePattern))
        {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();      
        try {
                HtmlManager.printBuyerHomePage(out,  category_list);
            } finally {            
            out.close();
                }
        }
        
        
        else if(op.equals(productsPattern))
        {  
            ArrayList products_list = null;

            try{
              products_list = DbManager.queryProductsList(Integer.parseInt(request.getParameter("category")));
                }
                catch (NumberFormatException e){
                response.sendRedirect(redirectURL);
                return;
                }
            
              
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
            try{     
                HtmlManager.printBuyerProdcutsPage(out,  category_list, products_list, Integer.parseInt(request.getParameter("category")));             
             } finally {            
             out.close();
             }
        }
         
        else 
        {     
            ArrayList order_list = null;
            String id = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(id);
            order_list = DbManager.queryBuyerOrders(user_id);
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
                HtmlManager.printBuyerOrdersPage(out, category_list, order_list , message , type);
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
