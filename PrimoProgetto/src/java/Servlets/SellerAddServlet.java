/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Product;
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
 * @author Alessandro
 */
public class SellerAddServlet extends HttpServlet {

    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    private String contextPath , ConfirmPattern , ResponsePattern , redirectURL , CancelPattern, ControllPattern;
    
    @Override
    public void init() throws ServletException {
            DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            contextPath = this.getServletContext().getContextPath();
            ConfirmPattern  = "confirm";
            ResponsePattern = "response";
            CancelPattern = "cancel";
            ControllPattern="controll";
            redirectURL = contextPath + "/Seller/SellerController?op=home";
        } 
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String op = request.getParameter("op");
        Product product = new Product();
        ArrayList category_list = DbManager.queryCategory(this.getServletContext());
        
        if(op.equals(ControllPattern))
        {
          int number;  
          /*try{
           * number = Integer.parseInt(request.getParameter("quantity"));
           * }
           * catch (NumberFormatException ex){
           * this.printErrorPage(response);
           * return;
           * }
           * try{
           * number = Integer.parseInt(request.getParameter("price"));
           * }
           * catch (NumberFormatException ex){
           * this.printErrorPage(response);
           * return;
           * }*/
           product = (Product) session.getAttribute("sold");
           product.setProduct_name(request.getParameter("nome"));
           product.setDescription(request.getParameter("description"));
           product.setUm(request.getParameter("um"));
           product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
           product.setPrice(Integer.parseInt(request.getParameter("price")));
           product.setImage_url(null);
           product.setSeller_name(session.getAttribute("user_id").toString());
           session.setAttribute("sold", product);
          
           response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HtmlManager.printSellerAddControllPage(out, category_list, product);
        } finally {            
            out.close();
          } 
        }
        
        
        else if(op.equals(ConfirmPattern))
        {
            if(Integer.parseInt(request.getParameter("categories"))<1 || Integer.parseInt(request.getParameter("categories"))>4)
              {
              this.printErrorPage(response);
              return;
              }
        
            
            product.setCategory_id(Integer.parseInt(request.getParameter("categories")));
            session.setAttribute("sold", product);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
         try {
            HtmlManager.printSellerAddFormPage(out, category_list );
         } finally {            
            out.close();
           } 
        }
        
        
        else if(op.equals(CancelPattern))
        {
            session.removeAttribute("order");
            response.sendRedirect(contextPath + "/Seller/SellerController?op=home");
        }
        
        
        else if(op.equals(ResponsePattern))
        {
        
            if(product == null)
            {
                this.printErrorPage(response);
                return;
            }
  
        if(DbManager.queryInsertNewProduct((Product)session.getAttribute("sold"), Integer.parseInt(session.getAttribute("user_id").toString()))) {
            response.sendRedirect(contextPath + "/Seller/SellerController?op=home&message=Il tuo prodotto e' stato immesso con successo.&type=1");   
            } 
        else
        {
            response.sendRedirect(contextPath + "/Seller/SellerController?op=home&message=Il tuo prodotto non e' stato immesso correttamente.&type=-1");
        
        }
        session.removeAttribute("sold");
        }
        
    }
    
    
    private void printErrorPage(HttpServletResponse response) throws IOException
    {
 
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            HtmlManager.printErrorPage(out, "SellerHome", redirectURL, contextPath);
            
        } finally {   
            
            out.close();
        } 
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