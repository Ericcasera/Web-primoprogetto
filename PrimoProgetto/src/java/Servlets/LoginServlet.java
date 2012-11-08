/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.User;
import Database.DBmanager;
import Database.HtmlManager;
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
public class LoginServlet extends HttpServlet {
    
    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    
    @Override
    public void init() throws ServletException {
            this.DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            this.HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
                User tmp = null;  
                
                tmp = DbManager.Autentication(username, password);

                if(tmp == null) { //Errore non sei nel database          
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();      
                    HtmlManager.printLoginPage(out, "Errore di autenticazione , Username o Password errati");      
                    out.close();
                }    
                else
                {   //Login accettato , creo la session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", username);
                    session.setAttribute("role", tmp.getRole());
                    
                    if(tmp.getRole() == 1) {
                        response.sendRedirect(request.getContextPath() + "/Buyer/BuyerHome.html");
                    }
                    else {
                        response.sendRedirect(request.getContextPath() + "/Seller/SellerHome.html");
                    }
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
