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
    private String contextPath , RequestPattern , ConfirmPattern , ResponsePattern , redirectURL , CancelPattern;
    
    @Override
    public void init() throws ServletException {
            DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            contextPath = this.getServletContext().getContextPath();
            PdfManager = new PdfManager(this.getServletContext());
            RequestPattern  = "request";
            ConfirmPattern  = "confirm";
            ResponsePattern = "response";
            CancelPattern = "cancel";
            redirectURL = contextPath + "/Buyer/BuyerController?op=home";
        } 
      
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String op = request.getParameter("op");
        Product product = null;
        ArrayList category_list = DbManager.queryCategory();
        
        if(op.equals(RequestPattern)) 
         
        {
        //Controllo input 
        try{       
            product = DbManager.queryProduct(Integer.parseInt(request.getParameter("product")));           
        }catch (NumberFormatException ex){
            response.sendRedirect(redirectURL);
            return;
        }
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
        else if(op.equals(ConfirmPattern)) //Pagina di riepilogo dove confermo o annullo l'ordine
        {
          int number;  
          //Controllo input
            try{       
                 number = Integer.parseInt(request.getParameter("number"));          
               }
                catch (NumberFormatException ex){
                  this.printErrorPage(response);
                  return;
               }  
           product = (Product) session.getAttribute("order");                    
           if(product == null)
            {
                this.printErrorPage(response);
                return;
            }
                    
           product.setQuantity(number); //Aggiorno la quantità da acquistare del prodotto e stampo la pagine di conferma
           session.setAttribute("order", product);
    
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HtmlManager.printBuyerOrderConfirmPage(out, category_list, product);
        } finally {            
            out.close();
        } 
        }
        else if(op.equals(ResponsePattern)) 
        {
        product = (Product) session.getAttribute("order");
        
        String prec_op = request.getParameter("prec_op");
                   
           if(product == null) //Controllo input
            {
                this.printErrorPage(response);
                return;
            }
           if(prec_op == null) 
           {
               session.removeAttribute("order");
               this.printErrorPage(response);
               return;
           }
          
        String result = null;
        session.removeAttribute("order");   
        
        if(DbManager.queryUpdateProdcuts(product)) { //Aggiorno la quantità del prodotto in vendita 
        //Se il metodo ritorna false vuol dire che non cerano abbastanza prodotti in vendità e quindi avviso l'utente e ridireziono   
        //Il caso false capita solo in caso di acquisto "contemporaneo" in quanto la quantità inserita viene controllata da javascript nella pagine di request    
                int order_id = DbManager.queryInsertBuyOrder(product, Integer.parseInt(session.getAttribute("user_id").toString())); //Inserisco l'orine del database. il metodo ritorna l'id del ordine inserito
                String receipt_path = PdfManager.buildReceipt(product , session.getAttribute("user").toString() , order_id); //Creo la fattura dell'ordine 
                DbManager.queryInsertReceipt(order_id, receipt_path);   //Inserisco la fattura e ridireziono con messaggio di successo  
                response.sendRedirect(contextPath + "/Buyer/BuyerController?op=orders&message=Il tuo ordine e' stato completato con successo.&type=1");   
            } 
        else
        {
            response.sendRedirect(contextPath + "/Buyer/BuyerController?op=orders&message=Il tuo ordine non e' stato completato.&type=-1");
        
        }
        }
        else if(op.equals(CancelPattern))//Caso di eliminazione dell'ordine , rimuove semplicemente l'attributo della sessione
        {
            product = (Product) session.getAttribute("order");   
            
            if(product == null)
            {
                this.printErrorPage(response);
                return;
            }
        
            int category_id = product.getCategory_id();
        
            session.removeAttribute("order");
            response.sendRedirect(contextPath + "/Buyer/BuyerController?op=products&category="+category_id);
        }
        
        
    }
    
    private void printErrorPage(HttpServletResponse response) throws IOException
    {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            
            HtmlManager.printErrorPage(out, "BuyerHome", redirectURL, contextPath);
            
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
