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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daniel
 */
public class BuyerBuyOrder extends HttpServlet {

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
        String uri = request.getRequestURI();
        String message = null;
        Product product = null;
        int type = 0;
        
        String product_id = request.getParameter("ID");
        try{
        
            product = DbManager.queryProduct(this.getServletContext(), Integer.parseInt(product_id));
           
        }catch (NumberFormatException ex){
            message = "L'id del prodotto non è corretto!!! Smettila di sfarzare con l'url";
            type = -1;
        }
        
        if(product != null ) {session.setAttribute("order", product);}

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BuyerBuyOrder</title>");            
            out.println("</head>");
            out.println("<body>");
            if(type == -1)
            {
            out.println("<h2>" + message + " </h2>");
            }
            else if(product == null) {
                out.println("<h2>Siamo spiacenti ma il prodotto da lei cercato non è stato trovato.</h2>");
            }
            else
            {
            out.println("<h1>Il Prodotto da te cercato è " + product.getProduct_name() + "</h1>");
            out.println("<img src=\""+ product.getImage_url() +"\" <br>");
            out.println("Quantità :" + product.getQuantity()+ " <br>");
            out.println("Prezzo :" + product.getPrice()+ " <br>");
            out.println("Unità di misura :" + product.getUm()+ " <br>");
            out.println("Venditore :" + product.getSeller_name()+ " <br>");
            out.println("Descrizione :" + product.getDescription()+ " <br>");
            }
            out.println("</body>");
            out.println("</html>");
            
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
