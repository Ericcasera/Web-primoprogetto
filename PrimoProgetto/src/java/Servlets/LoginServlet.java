/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Beans.User;
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
public class LoginServlet extends HttpServlet {
    
    private HtmlManager HtmlManager;
    private DBmanager DbManager;
    private String contextPath , loginPattern , logoutPattern ;
    
    @Override
    public void init() throws ServletException {
            DbManager = (DBmanager)super.getServletContext().getAttribute("DbManager");
            HtmlManager = (HtmlManager)super.getServletContext().getAttribute("HtmlManager");
            contextPath = this.getServletContext().getContextPath();
            loginPattern = "login";
            logoutPattern = "logout";           
        }        
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String op = request.getParameter("op");
      
        //Dopo il logout richiamo questa servlet che stampa a video "logout effettuato"
        if(op.equals(logoutPattern)) //Controllo se sto facendo logout
        {
                    HttpSession session = request.getSession(false);
                    if(session != null)
                    {
                        session.invalidate();
                    }
                    request.setAttribute("message", "Logout effettuato con successo");
                    request.getRequestDispatcher("LoginController?op=login").forward(request, response);
                    return;
        }
        
        if(request.getAttribute("message")!= null) //Caso che arrivo dopo il logout
        {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();      
        HtmlManager.printLoginPage(out, (String)request.getAttribute("message") , 0);      
        out.close(); 
        return;
        } 
        
        //Caso che arrivo avendo premuto il tasto login
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
                User tmp = null;  
                
                tmp = DbManager.Autentication(username, password);
                //Caso autenticazione fallita
                if(tmp == null) {        
                    response.setContentType("text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();      
                    HtmlManager.printLoginPage(out, "Errore di autenticazione, Username o Password errati" , -1);      
                    out.close();
                }    
                else
                {   //Login accettato , creo la session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", username);
                    session.setAttribute("user_id", tmp.getId());
                    session.setAttribute("role", tmp.getRole());
                    
                    if(tmp.getRole() == 1) {
                        response.sendRedirect(contextPath + "/Buyer/BuyerController?op=home");
                    }
                    else {
                        response.sendRedirect(contextPath + "/Seller/SellerController?op=home");
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
