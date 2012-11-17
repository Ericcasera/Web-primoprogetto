/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class HtmlManager {
    
    public void printLoginPage(PrintWriter out , String message , int type)
          {
           out.println("<!DOCTYPE HTML>");   
           out.println("<html>");
           out.println("<head>");
           out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
           out.println("<link  href=\"Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
           out.println("<link  href=\"Bootstrap/css/grafica.css\" rel=\"stylesheet\">");
           out.println("<script src=\"Bootstrap/js/jquery-1.8.2.js\"></script>");
           out.println("<script src=\"Bootstrap/js/bootstrap.min.js\"></script>");
           out.println("<title>Login</title>");
           out.println("</head>");
           out.println("<body>");
           out.println("<div class=\"login well\">");
           out.println("    <div class=\"login-title\">");
           out.println("        <h4>Benvenuti a \"vendo piante\" , prego loggarsi</h4><br>");
           out.println("    </div>");         
           out.println("        <form action=\"Login\" method=\"post\" class=\"form-horizontal\">");          
           out.println("        <div class=\"control-group\">");
           out.println("            <label class=\"control-label\" for=\"username\">Username</label>");
           out.println("                <div class=\"controls\">");
           out.println("                <input class=\"input-large\" placeholder=\"Username\" type=\"text\" id=\"username\" name=\"username\">");
           out.println("                </div>");
           out.println("         </div>");  
           out.println("        <div class=\"control-group\">");
           out.println("            <label class=\"control-label\" for=\"password\">Password</label>");
           out.println("                <div class=\"controls\">");
           out.println("                <input class=\"input-large\" placeholder=\"Password\" type=\"password\" id=\"password\" name=\"password\">");
           out.println("                </div>");
           out.println("            </div>");   
           out.println("        <div class=\"control-group\">");
           out.println("            <div class=\"controls\">");
           out.println("                <button class=\"btn\" type=\"submit\">Login</button>");
           out.println("                <button class=\"btn\" type=\"reset\" >Reset</button>");
           out.println("            </div>");
           out.println("        </div>");
           
           if(message != null)
             {  
                out.println("<div align=\"center\" class=\"control-group\">");
                if(type == -1)
                {
                out.println("   <div class=\"alert alert-error fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <p algin=\"center\" class=\"text-error\"> " + message + "</p>  ");
                out.println("   </div>");
                }
                else
                {
                out.println("   <div class=\"alert alert-success fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <p algin=\"center\" class=\"text-success\"> " + message + "</p>  ");
                out.println("   </div>");
                }     
                out.println("</div>");     
             }  

           out.println("    </form>");
           out.println("    </div>");
           out.println("</body>");
           out.println("</html>");   

          }
    
    static public void printErrorPage(PrintWriter out, String message , String redirectURL) //String message
    {
    out.println("<!DOCTYPE HTML>");       
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");     
    out.println("<link href=\""+redirectURL+"/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<title>ErrorPage</title>");
    out.println("    <script type=\"text/javascript\">");
    out.println("       var ss = 5;");
    out.println("           function countdown() {");
    out.println("               ss = ss-1;");
    out.println("               if (ss==0) {");
    out.println("               window.location=\""+ redirectURL +"\";");
    out.println("           }");
    out.println("               else {");
    out.println("                   document.getElementById(\"countdown\").innerHTML=ss;");
    out.println("                   window.setTimeout(\"countdown()\", 1000);");
    out.println("               }}");
    out.println("    </script>");
    out.println("</head>");
    out.println("");
    out.println("<body onload=\"countdown()\">");
    out.println("    <center>");
    out.println("        <h4 class=\"text-error\">Non sei autorizzato ad accedere a questa pagina. <br> Sarei reindirizzato a breve <a href=\""+redirectURL+"\">"+message+"</a>(<span id=\"countdown\" style=\"color:green;\">5</span>)</h4>");
    out.println("    </center>");
    out.println("</body>");
    out.println("</html>");

    }
    
    public void printBuyerHomePage(ArrayList category_list , String username)
    {
    
    
    
    }
    
    public void printBuyerProdcutsPage(ArrayList category_list , ArrayList products_list ,String username , String message , int type)
    {
    //type = 1 per messaggio postitivo (acquisto eseguito con successo)
    //type = -1 per messaggio negativo (categoria non trovata , acquisto non eseguito)
    
    }
    
    public void printBuyerOrdersPage(ArrayList category_list , ArrayList orders_list , String username)
    {

    
    }
    
    
    
    
    
    
}
