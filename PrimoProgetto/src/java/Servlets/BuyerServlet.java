/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Category;
import Beans.Order;
import Beans.Product;
import Managers.DBmanager;
import Managers.HtmlManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
    private String contextPath;
    
    @Override
    public void init() throws ServletException {
            this.DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            this.HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            this.contextPath = this.getServletContext().getContextPath();
            /*La query categoria si puo mettere qua (dato che non cambia mai) oppure fare un meccanismo ci chasing che
            * tenga conto delle modifiche oppure fare un metodo (tipo un url speciale) che aggiorna le categorie
            */
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        
  
        HttpSession session = request.getSession(false);
        ArrayList lista = DbManager.queryCategory(this.getServletContext());
        String uri = request.getRequestURI();
        
        if(uri.equals(contextPath + "/Buyer/BuyerHome"))
        {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BuyerHome</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Benevenuto in buyer : " + (String) session.getAttribute("user") );
            out.println("<br><h1>Il mio path è : " + request.getContextPath());
            out.println("<br><a href=\"/PrimoProgetto/Logout\" > Logout </a>");
            out.println("<br><a href=\"/PrimoProgetto/Buyer/Orders\" > I miei ordini </a>"); 
            out.println("<table><tr><td>ID</td><td>Nome</td><td>Descrizione</td><td>Image_ULR</td></tr>");
            Iterator iter = lista.iterator();
            while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            out.println("<tr><td>" + tmp.getId() + "</td>");
            out.println("<td><a href=\""+ contextPath +"/Buyer/Products?category="+ tmp.getId()+"\">" + tmp.getName() + " </a> </td>");
            out.println("<td>" + tmp.getDescription() + "</td>");
            out.println("<td><img src=\""+tmp.getImageURL() +"\" width=\"200px\" height=\"200px\"></td></tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        }
        else if(uri.equals(contextPath + "/Buyer/Products"))
        {
            String error = null;       
            ArrayList products_list = null;
            if(request.getParameter("category") == null)
            {
            error = "Categoria non trovata";
            }
            else
            {
                try{
              products_list = DbManager.queryProductsList(this.getServletContext(), Integer.parseInt(request.getParameter("category")));
                }
                catch (NumberFormatException e){
                error = e.toString();
                }
            }
                   
            
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
            try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ProductPage</title>");            
            out.println("</head>");
            out.println("<body>");
            if(error != null)
            {
            out.println("<h1>Ce stato un errore :" + error + "<h1><br>");
            }
            else if(products_list.isEmpty())
            {
            out.println("<h1>Non ci sono elementi in questa categoria<h1><br>");
            }
            else
            {
            out.println("<table><tr><td>Nome</td><td>Descrizione</td><td>Um</td><td>Price</td><td>Quantity</td></tr>");           
            Iterator iter = products_list.iterator();
            while(iter.hasNext())
            {
            Product tmp = (Product) iter.next();
            out.println("<tr><td><a href=\"BuyRequest?ID="+ tmp.getProduct_id() +"\">"+ tmp.getProduct_name()+"</td>");
            out.println("<td>" +     tmp.getDescription() + "</td>");
            out.println("<td>" +     tmp.getUm() + "</td>");
            out.println("<td>" +     tmp.getPrice() + "</td>");
            out.println("<td>" +     tmp.getQuantity() + "</td></tr>");
            }
            out.println("</table>"); 
            }        
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
        }
        else
        {     
            ArrayList order_list = null;
            String id = session.getAttribute("user_id").toString();
            int user_id = Integer.parseInt(id);
            order_list = DbManager.queryBuyerOrders(this.getServletContext(), user_id);
            
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();  
            try{
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ProductPage</title>");            
            out.println("</head>");
            out.println("<body>");
            if(order_list.isEmpty())
            {
            out.println("<h1>Non ci sono elementi in questa categoria<h1><br>");
            }
            else
            {
            out.println("<table><tr><td>ID</td><td>Nome</td><td>prezzo</td><td>Um</td><td>quantita</td><td>prezzo totale</td><td>Data ordine</td></tr>");           
            Iterator iter = order_list.iterator();
            while(iter.hasNext())
            {
            Order tmp = (Order) iter.next();
            out.println("<tr><td>" + tmp.getOrder_id() + "</td>");
            out.println("<td>"+ tmp.getProduct_name() + "</td>");
            out.println("<td>" + tmp.getPrice() + "</td>");
            out.println("<td>" + tmp.getUm() + "</td>");
            out.println("<td>" + tmp.getQuantity() + "</td>");
            out.println("<td>" + tmp.getTotal_price() + "</td>");
            out.println("<td>" + tmp.getOrder_date() + "</td></tr>");
            }
            out.println("</table>"); 
            }        
            out.println("</body>");
            out.println("</html>");
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
