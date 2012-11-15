/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.Category;
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
 */
public class BuyerServlet extends HttpServlet {

    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    
    @Override
    public void init() throws ServletException {
            this.DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            this.HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {  
        
        
        
        HttpSession session = request.getSession(false);
        ArrayList lista = DbManager.queryCategory(super.getServletContext());
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BuyerServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Benevenuto in buyer : " + (String) session.getAttribute("user") );
            out.println("<br><h1>Il mio path è : " + request.getContextPath());
            out.println("<br><a href=\"/PrimoProgetto/Logout\" > Logout </a>"); 
            out.println("<table><tr><td>ID</td><td>Nome</td><td>Descrizione</td><td>Image_ULR</td></tr>");
            Iterator iter = lista.iterator();
            while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            out.println("<tr><td>" + tmp.getId() + "</td>");
            out.println("<td>" + tmp.getName() + "</td>");
            out.println("<td>" + tmp.getDescription() + "</td>");
            out.println("<td><img src=\"http://localhost:8084/PrimoProgetto/Images/mela.jpg\" width=\"200px\" height=\"200px\"></td></tr>");
            }
            out.println("</table>");
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
