/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.PrintWriter;

/**
 *
 * @author Daniel
 */
public class HtmlManager {
    
    public void printLoginPage(PrintWriter out , String message)
          {
              
           out.println("<html>");
           out.println("<head>");
           out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
           out.println("<link href=\"Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
           out.println("<title>Login</title>");
           out.println("</head>");
           out.println("<body>");
           out.println("    <div>");
           
           out.println("        <form action=\"Login\" method=\"post\" class=\"form-horizontal\">");
           
           out.println("        <div class=\"control-group\">");
           out.println("            <label class=\"control-label\" for=\"username\">Username</label>");
           out.println("                <div class=\"controls\">");
           out.println("                <input type=\"text\" id=\"username\" name=\"username\">");
           out.println("                </div>");
           out.println("         </div>");
           
           out.println("        <div class=\"control-group\">");
           out.println("            <label class=\"control-label\" for=\"password\">Password</label>");
           out.println("                <div class=\"controls\">");
           out.println("                <input type=\"password\" id=\"password\" name=\"password\">");
           out.println("                </div>");
           out.println("            </div>");  
           
           if(message != null)
             {  
           out.println("        <div class=\"control-group\">");
           out.println("                <div class=\"controls\">");
           out.println("                <p class=\"text-error\"> " + message + "</p> ");
           out.println("                </div>");
           out.println("         </div>");
             } 
  
           out.println("        <div class=\"control-group\">");
           out.println("            <div class=\"controls\">");
           out.println("                <button class=\"btn\" type=\"submit\">Login</button>");
           out.println("                <button class=\"btn\" type=\"reset\" >Reset</button>");
           out.println("            </div>");
           out.println("        </div>");
           out.println("    </form>");
           out.println("    </div>");
           out.println("</body>");
           out.println("</html>");   

          }
    
    
    
    
}
