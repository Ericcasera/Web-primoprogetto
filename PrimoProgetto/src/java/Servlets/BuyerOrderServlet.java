/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Product;
import Managers.DBmanager;
import Managers.HtmlManager;
import Managers.PdfManager;
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
public class BuyerOrderServlet extends HttpServlet {

    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    private PdfManager PdfManager;
    private String contextPath , RequestPattern , ConfirmPattern , ResponsePattern , redirectURL;
    
    @Override
    public void init() throws ServletException {
            DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            contextPath = this.getServletContext().getContextPath();
            PdfManager = new PdfManager();
            RequestPattern  = "request";
            ConfirmPattern  = "confirm";
            ResponsePattern = "response";
            redirectURL = contextPath + "/BuyerController?op=home";
        } 
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String op = request.getParameter("op");
        Product product = null;
        ArrayList category_list = DbManager.queryCategory(this.getServletContext());
        
        if(op.equals(RequestPattern))
         
        {
        //Controllo che l'input sia un id   
        try{       
            product = DbManager.queryProduct(this.getServletContext(), Integer.parseInt(request.getParameter("product")));           
        }catch (NumberFormatException ex){
            response.sendRedirect(redirectURL);
            return;
        }
        //Controllo che ci sia un prodotto
        if(product == null)
            {
                response.sendRedirect(redirectURL);
                return;
            }
        session.setAttribute("order", product);
              
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            HtmlManager.printBuyerOrderRequestPage(out, category_list , product);

        } finally {            
            out.close();
        }
        
        }
        else if(op.equals(ConfirmPattern))
        {
          int number;  
          //Controllo l'input
            try{       
                 number = Integer.parseInt(request.getParameter("number"));          
               }
                catch (NumberFormatException ex){
                  response.sendRedirect(redirectURL);
                  return;
               }  
          //Controllo che ci sia un prodotto da acquistare in sessione
           product = (Product) session.getAttribute("order");
           if(product == null)
            {
                response.sendRedirect(redirectURL);
                return;
            }
           
           
           product.setQuantity(number);
           session.setAttribute("order", product);
    
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

        } finally {            
            out.close();
        } 
        }
        else
        {
        product = (Product) session.getAttribute("order");
        
        if(product == null)
            {
                response.sendRedirect(redirectURL);
                return;
            }   
        
        boolean result = false;
        
                
        //result = DbManager.queryInsertBuyOrder(product, Integer.parseInt(session.getAttribute("user_id").toString()));
        
        // boolean result = DbManager.queryUpdateProdcuts(product);
        /* if(result)
         *      String sha1 = Pdfmanager.createRecepit();
         *      insertOrder (product , sha1);
         *      sessionRemove(order);
         *      sendRedirect : (products , success , category_id);
        *   else    
        *       sessionRemove(Order);
        *       sendRedirect : (prodcuts , error , category_id);
        */        
        
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
