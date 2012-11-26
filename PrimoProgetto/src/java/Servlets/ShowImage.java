/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
public class ShowImage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
          String path = this.getServletContext().getRealPath("Images/"); 
          String files;
          File folder = new File(path);
          File[] listOfFiles = folder.listFiles(); 
    
          response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ShowImage</title>");  
            out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
            out.println("<link rel=\"stylesheet\" href=\"Bootstrap/css/bootstrap.css\" />  ");
            out.println("<script src=\"Bootstrap/js/jquery-1.8.2.js\"></script>");
            out.println("<sciprt src=\"Bootstrap/css/bootstrap.min.js\"></sciprt>");
            out.println("<script>");
            out.println("$(function() {");
            out.println("$( \"#select_image\" ).click(function() {");
            out.println("       $( \"#form_div\" ).hide();");
            out.println("       $( \"#image_div\").show();");
            out.println("   return false;});");
            out.println("    });");
            out.println("function changeImage(image_name){");
            out.println("   document.getElementById(\"image_preview\").src = '/PrimoProgetto/Images/' + image_name;");
            out.println("   document.getElementById(\"image_name\").value = image_name;");
            out.println("       $(function() {");
            out.println("           $(\"#image_div\").hide();");
            out.println("           $( \"#form_div\" ).show();});");
            out.println("     return false };");
            out.println(" </script>");      
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"container\">");
            out.println("   <center><div id=\"form_div\" >");
            out.println("               <a href=\"#\" id=\"select_image\" onClick=\"return false;\">");  
            out.println("               <img id=\"image_preview\" src=\"...\" width=\"150px;\" height=\"150px;\" class=\"img-polaroid\"><br>");
            out.println("               </a>");
            out.println("       <form action=\"#\" method=\"get\">");
            out.println("           Attributo 1<input type=\"text\" > <br>");
            out.println("           Attributo 2<input type=\"text\" > <br>");
            out.println("           Image name<input id=\"image_name\" type=\"text\" > </form> </center> </div>");
            out.println("   <div id=\"image_div\" class=\"hide\" >");     
            for (int i = 0; i < listOfFiles.length; i++) 
            {
                 if (listOfFiles[i].isFile()) 
                 {
                files = listOfFiles[i].getName();
                out.println("<a href=\"#\" onClick=\"return changeImage('"+files+"');\"> ");
                out.println("<img src=\"/PrimoProgetto/Images/" + files + "\" width=\"150\" height=\"150\"></h2>");
                out.println("</a>");
                 }
            }
            out.println("</div></div>");
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
