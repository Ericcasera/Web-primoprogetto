/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Category;
import Beans.Order;
import Beans.Product;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletContext;

/**
 *
 * @author Daniel
 */
public class HtmlManager {
    
    private ServletContext context;
    private String contextPath;
    
    public HtmlManager(ServletContext context)
    {
        this.context = context;
        this.contextPath = context.getContextPath();
    }
    
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
           out.println("        <br><img src=\"" + contextPath + "/Images-site/logo_login.png\"><br><br><br>");
           out.println("    </div>");         
           out.println("        <form action=\"LoginController?op=login\" method=\"post\" class=\"form-horizontal\">");          
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
    
    static public void printErrorPage(PrintWriter out, String message , String redirectURL , String contextPath) //String message
    {
    out.println("<!DOCTYPE HTML>");       
    out.println("<html>");
    out.println("<head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");     
    out.println("<link href=\""+contextPath+"/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
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
    out.println("    <br><br><h1>Non sei autorizzato ad accedere a questa pagina</h1>");
    out.println("   <img src=\"/PrimoProgetto/Images-site/peanutbutterjellytime.gif\">");
    out.println("        <h5 >Sarai reindirizzato a breve a questo indirizzo <a href=\""+redirectURL+"\">"+message+"</a>(<span id=\"countdown\">5</span>)</h5>");
    out.println("    </center>");
    out.println("</body>");
    out.println("</html>");

    }
    
    public void printBuyerHomePage(PrintWriter out, ArrayList category_list)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("   <title>HomePage</title> </head> <body>");
    
    this.printPageHeader(out, category_list);
    
    out.println("       <div class=\"span10\">");
    out.println("           <table class=\"table\"> <tbody>");
    Iterator iter = category_list.iterator();
            while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImageURL() +"\" width=\"200\" height=\"200\" alt=\""+ tmp.getName() +"\"></td>");
            out.println("<td><a href=\"BuyerController?op=products&category=" + tmp.getId() + "\">"
                    + "<h4>"+ tmp.getName() +"</h4></a>" + tmp.getDescription() + "</td></tr>");
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>");
    
    
    }
    
    public void printBuyerProdcutsPage(PrintWriter out, ArrayList category_list , ArrayList products_list , int category_id)
    {
    
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/jquery-1.8.2.js\"></script>");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/bootstrap.min.js\"></script>");
    out.println("   <title>Lista Prodotti</title> </head> <body>");

    String category_name = this.printProductPageHeader(out, category_list , category_id);
        
    out.println("       <div class=\"span10\">"); 
    out.println("           <h3>"+ category_name +"</h3>");
        if(products_list.isEmpty()) {
            out.println("<h3>Non ci sono prodotti per questa categoria</h3>");
        }  
    out.println("           <table class=\"table\"> <tbody>");
    Iterator iter = products_list.iterator();
            while(iter.hasNext())
            {
            Product tmp = (Product) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImage_url() +"\" width=\"100\" height=\"100\" alt=\""+tmp.getProduct_name()+"\"></td>");
            out.println("<td><a href=\"BuyerOrderController?op=request&product=" + tmp.getProduct_id() + "\">"
                    + "<h5>"+ tmp.getProduct_name() +"</h5>"
                    + "</a><strong>Prezzo : <span style=\"color:red\">" + tmp.getPrice() + "</span></strong>$");
            out.println("<br><strong>Disponibilità : </strong>" + tmp.getQuantity() +" " + tmp.getUm());
            out.println("<br><p> <small> "+tmp.getDescription() +" </small></p>");
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>"); 
    }
    
    
    public void printBuyerOrdersPage(PrintWriter out, ArrayList category_list , ArrayList orders_list , String message , int type)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\""  + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/jquery-1.8.2.js\"></script>");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/bootstrap.min.js\"></script>");
    out.println("   <title>I miei ordini</title> </head> <body>");

    this.printPageHeader(out, category_list);
        
    out.println("       <div class=\"span10\">");
    out.println("<h3>I miei ordini</h3>");
        if(orders_list.isEmpty()) {
            out.println("<h3>Non ci sono ordini effettuati</h3>");
        }
    if(message != null)
             {  
                out.println("<div style=\"text-align:center\">");
                if(type == -1)
                {
                out.println("   <div class=\"alert alert-error fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <h4><p algin=\"center\" class=\"text-error\"> " + message + "</p></h4> ");
                out.println("   </div>");
                }
                else
                {
                out.println("   <div class=\"alert alert-success fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <h4><p algin=\"center\" class=\"text-success\"> " + message + "</p></h4> ");
                out.println("   </div>");
                }     
                out.println("</div>");     
             }   
        
    out.println("           <table class=\"table\"> <tbody>");
    Iterator iter = orders_list.iterator();
            while(iter.hasNext())
            {
            Order tmp = (Order) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImage_url() +"\" width=\"100\" height=\"100\" alt=\""+tmp.getProduct_name()+"\"></td>");
            out.println("<td class=\"span6\"><h4>"+ tmp.getProduct_name() +"</h4>"
                    + "<strong>Ordinato in data : </strong>" + tmp.getOrder_date() + "<br>"
                    + "<strong>Ordine : </strong>#"+tmp.getOrder_id()+"<br>"
                    + "<strong>Venditore : </strong>" + tmp.getSeller_name()+ "</td>");
            out.println("<td>"
                    + "<strong><br>Prezzo: </strong>" + tmp.getPrice() + "$ * ");
            out.println(tmp.getQuantity()+" "+tmp.getUm()+" <br>");
            out.println("--------------------------------------------<br>");
            out.println("<strong>Totale : <span style=\"color:red\">" + tmp.getTotal_price() + "</span></strong>$<br>");
            out.println("<strong>Fattura : </strong><a href=\"" + contextPath + "/"+tmp.getReceipt_url()+"\" >Fattura</a></td></tr>");
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>");     
      
    }
    
    public void printBuyerOrderRequestPage(PrintWriter out , ArrayList category_list , Product product)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script type=\"text/javascript\">");
    
    out.println("function calculate(){");
    out.println("   var num = parseInt(document.getElementById(\"number\").value);");
    out.println("   var tot = num * "+product.getPrice()+";");
    out.println("   document.getElementById(\"totale\").innerHTML = +tot ;");
    out.println("   document.getElementById(\"update\").innerHTML = \"\";}"); 
    
    out.println("function check() {");
    out.println("   var num = parseInt(document.getElementById(\"number\").value);");
    out.println("   var max = "+product.getQuantity()+";");
    out.println("if(num <=0 ) document.getElementById(\"number\").value = 1;");
    out.println("else if(num >max) document.getElementById(\"number\").value = max;");
    out.println("document.getElementById(\"update\").innerHTML = \"(aggiorna prezzo)\"; }");
    
    out.println("function validate(form){");
    out.println("   if(document.getElementById(\"update\").innerHTML != \"\")");
    out.println("   {"); 
    out.println("           document.getElementById(\"messaggio\").innerHTML = \"*Devi aggiornare il prezzo prima di avanzare\"; ");
    out.println("           return false;}");
    out.println("    else return true;");
    out.println("    }"); 
    out.println("    </script> ");
    
    out.println("   <title>Carello</title> </head> <body>");
    
    this.printPageHeader(out, category_list);
       out.println("       <div class=\"span10\">");
       out.println("<h3>Il mio carrello</h3>");
       out.println("           <table class=\"table\"> <tbody>");
       out.println("<tr><td class=\"span5\"><img src=\"" + contextPath + "/"+ product.getImage_url() +"\" width=\"300\" height=\"300\" alt=\""+product.getProduct_name()+"\"></td>");
       out.println("<td><p><h4>"+ product.getProduct_name() + "</h4>");   
       out.println("<small>"+product.getDescription()+"</small><br>");
       out.println("<strong>Venduto da : </strong> "+product.getSeller_name() + "<br>");
       out.println("<strong>Prezzo : </strong> "+product.getPrice() + "$<br>");
       out.println("<strong>Disponibili : </strong> "+product.getQuantity() + " " + product.getUm() + "<br>");
       out.println("--------------------------------------------<br></p>");
       out.println("<strong>Prezzo totale : <span style=\"color:red\" id=\"totale\">"+product.getPrice()+"</span></strong>$"); 
       out.println("                  &ensp;<span style=\"color:red\" id=\"messaggio\"></span><br></p>");
       out.println("        <form action=\"BuyerOrderController?op=confirm\" onsubmit=\"return validate(this)\" method=\"post\">");          
       out.println("        <div class=\"control-group\">");
       out.println("            <span><strong>Quantità</strong></span>&ensp;<a href=\"#\" onclick=\"calculate(); return false\" id=\"update\"></a>");
       out.println("                <div class=\"controls\">");
       out.println("                <input class=\"input-small\" type=\"number\" id=\"number\" name=\"number\" value=\"1\" onchange=\"check()\">");
       out.println("                </div>");
       out.println("        </div>");  
       out.println("        <div class=\"control-group\">");
       out.println("            <div class=\"controls\">");
       out.println("                <button class=\"btn\" type=\"submit\">Conferma ordine</button>");
       out.println("                <a type=\"button\" class=\"btn\" href=\"BuyerController?op=products&category="+product.getCategory_id()+"\">Annulla ordine</a>");
       out.println("            </div>");
       out.println("        </div> </td></tr>");
       out.println("           </tbody> </table> </div> </div> </div>");
       out.println("   </body>");
       out.println("</html>"); 
    }
    
    public void printBuyerOrderConfirmPage(PrintWriter out , ArrayList category_list ,Product product)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");   
    out.println("   <title>Conferma ordine</title> </head> <body>");
    
    this.printPageHeader(out, category_list);
       out.println("       <div class=\"span10\">");
       out.println("<h3>Conferma ordine</h3>");
       out.println("           <table class=\"table\"> <tbody>");
       out.println("<tr><td class=\"span5\"><img src=\"" + contextPath + "/"+ product.getImage_url() +"\" width=\"300\" height=\"300\" alt=\""+product.getProduct_name()+"\"></td>");
       out.println("<td><p><h4>"+ product.getProduct_name() + "</h4>");   
       out.println("<small>"+product.getDescription()+"</small><br>");
       out.println("<strong>Venduto da : </strong>"+product.getSeller_name() + "<br>");
       out.println("<strong>Prezzo : </strong>"+product.getPrice() + "$<br>");
       out.println("<strong>Pezzi acquistati : </strong>"+product.getQuantity() + " " + product.getUm() + "<br>");
       out.println("--------------------------------------------<br></p>");
       out.println("<strong>Prezzo totale ordine : <span style=\"color:red\" id=\"totale\">"+product.getPrice()*product.getQuantity()+"</span></strong>$"); 
       out.println("                  &ensp;<span style=\"color:red\" id=\"messaggio\"></span><br></p>");
       out.println("        <form action=\"BuyerOrderController?op=response\" method=\"post\">");     
       out.println("                <input type=\"hidden\" name=\"prec_op\" value=\"confirm\">");
       out.println("        <div class=\"control-group\">");
       out.println("            <div class=\"controls\">");
       out.println("                <button class=\"btn\" type=\"submit\">Conferma ordine</button>");
       out.println("                <a type=\"button\" class=\"btn\" href=\"BuyerOrderController?op=cancel\">Annulla ordine</a>"); 
       out.println("            </div>");
       out.println("        </div> </td></tr>");
       out.println("           </tbody> </table> </div> </div> </div>");
       out.println("   </body>");
       out.println("</html>"); 
    }
    
       
    //Questo stampa solo l'header
    private void printPageHeader(PrintWriter out , ArrayList category_list)
    {
    out.println("   <div class=\"row-fluid\">");
    out.println("       <div class=\"span12 \">");
    out.println("           <div class=\"row-fluid\">");
    out.println("               <div class=\"span10\"><img src=\"" + contextPath + "/Images-site/logo.jpg\" alt=\"logo\"> </div>");
    out.println("   </div></div></div><br>");
    out.println("<div class=\"container-fluid\">");
    out.println("  	<div class=\"row-fluid\">");
    out.println("           <div class=\"span2\" style=\"min-width:140px\">");
    out.println("               <ul class=\"nav nav-list\">");
    out.println("                   <li  ><a href=\"BuyerController?op=home\">Home</a></li>");
    out.println("                   <li class=\"nav-header\">Il mio account</li>");
    out.println("                   <li><a href=\"BuyerController?op=orders\">I miei ordini</a></li>");
    out.println("                   <li><a href=\"" + contextPath + "/LoginController?op=logout\">Logout</a></li>");
    out.println("                   <li class=\"nav-header\">Categorie</li>");
    Iterator iter = category_list.iterator();
    while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            out.println("<li><a href=\"BuyerController?op=products&category="+tmp.getId()+"\">"+tmp.getName()+"</a></li>");
            }    
    out.println("		</ul></div>");
    }
       
    //Questo ritorna il nome della categoria del prodotto
    private String printProductPageHeader(PrintWriter out , ArrayList category_list, int category_id)
    {
    out.println("   <div class=\"row-fluid\">");
    out.println("       <div class=\"span12 \">");
    out.println("           <div class=\"row-fluid\">");
    out.println("               <div class=\"span10\"><img src=\"" + contextPath + "/Images-site/logo.jpg\" alt=\"logo\"> </div>");
    out.println("   </div></div></div><br>");
    out.println("<div class=\"container-fluid\">");
    out.println("  	<div class=\"row-fluid\">");
    out.println("           <div class=\"span2\" style=\"min-width:140px\">");
    out.println("               <ul class=\"nav nav-list\">");
    out.println("                   <li  ><a href=\"BuyerController?op=home\">Home</a></li>");
    out.println("                   <li class=\"nav-header\">Il mio account</li>");
    out.println("                   <li><a href=\"BuyerController?op=orders\">I miei ordini</a></li>");
    out.println("                   <li><a href=\"" + contextPath + "/LoginController?op=logout\">Logout</a></li>");
    out.println("                   <li class=\"nav-header\">Categorie</li>");
    Iterator iter = category_list.iterator();
    String result = null;
    while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            if(category_id == tmp.getId()) {
                    result = tmp.getName();
                }
            out.println("<li><a href=\"BuyerController?op=products&category="+tmp.getId()+"\">"+tmp.getName()+"</a></li>");
            }    
    out.println("		</ul></div>");
        return result;
    }
    
    //Header per il seller 
    private void printPageHeaderSeller(PrintWriter out , ArrayList category_list)
    {
    out.println("   <div class=\"row-fluid\">");
    out.println("       <div class=\"span12\">");
    out.println("           <div class=\"row-fluid\">");
    out.println("               <div class=\"span10\"><img src=\"" + contextPath + "/Images-site/logo.jpg\" alt=\"logo\"> </div>");
    out.println("   </div></div></div><br>");
    out.println("<div class=\"container-fluid\">");
    out.println("  	<div class=\"row-fluid\">");
    out.println("           <div class=\"span2\" style=\"min-width:140px\">");
    out.println("               <ul class=\"nav nav-list\">");
    out.println("                   <li  ><a href=\"SellerController?op=home\">Home</a></li>");
    out.println("                   <li class=\"nav-header\">Il mio account</li>");
    out.println("                   <li><a href=\"SellerController?op=myStore\">I miei prodotti</a></li>");
    out.println("                   <li><a href=\"SellerController?op=addProduct\">Aggiungi prodotto</a></li>");
    out.println("                   <li><a href=\"" + contextPath + "/LoginController?op=logout\">Logout</a></li>");
    out.println("                   <li class=\"nav-header\">Categorie</li>");
    Iterator iter = category_list.iterator();
    while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            out.println("<li><a href=\"SellerController?op=products&category="+tmp.getId()+"\">"+tmp.getName()+"</a></li>");
            }    
    out.println("		</ul></div>");
    }
    
    //Header per il seller che come per il buyer ritorna il nome della categoria
    private String printProductPageHeaderSeller(PrintWriter out , ArrayList category_list, int category_id)
    {
    out.println("   <div class=\"row-fluid\">");
    out.println("       <div class=\"span12 \">");
    out.println("           <div class=\"row-fluid\">");
    out.println("               <div class=\"span10\"><img src=\"" + contextPath + "/Images-site/logo.jpg\" alt=\"logo\"> </div>");
    out.println("   </div></div></div><br>");
    out.println("<div class=\"container-fluid\">");
    out.println("  	<div class=\"row-fluid\">");
    out.println("           <div class=\"span2\" style=\"min-width:140px\">");
    out.println("               <ul class=\"nav nav-list\">");
    out.println("                   <li  ><a href=\"SellerController?op=home\">Home</a></li>");
    out.println("                   <li class=\"nav-header\">Il mio account</li>");
    out.println("                   <li><a href=\"SellerController?op=myStore\">I miei prodotti</a></li>");
    out.println("                   <li><a href=\"SellerController?op=addProduct\">Aggiungi prodotto</a></li>");
    out.println("                   <li><a href=\"" + contextPath + "/LoginController?op=logout\">Logout</a></li>");
    out.println("                   <li class=\"nav-header\">Categorie</li>");
    Iterator iter = category_list.iterator();
    String result = null;
    while(iter.hasNext())
            {
            Category tmp = (Category) iter.next();
            if(category_id == tmp.getId()) {
                    result = tmp.getName();
                }
            out.println("<li><a href=\"SellerController?op=products&category="+tmp.getId()+"\">"+tmp.getName()+"</a></li>");
            }    
    out.println("		</ul></div>");
        return result;
    }
    
    public void printSellerHomePage(PrintWriter out, ArrayList category_list, ArrayList sell_order_list)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" +  contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("   <title>Home Page</title> </head> <body>");
    
    this.printPageHeaderSeller(out, category_list);
    
    out.println("       <div class=\"span10\">");
    out.println("<h3>Ordini ricevuti</h3>");

    out.println("           <table class=\"table\"> <tbody>");
    Iterator iter = sell_order_list.iterator();
    if(sell_order_list.isEmpty()){
        out.println("<h3>Non hai ancora ricevuto ordini</h3>");
    }
           out.println("           <table class=\"table\"> <tbody>");
            while(iter.hasNext())
            {
            Order tmp = (Order) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImage_url() +"\" width=\"100\" height=\"100\" alt=\""+tmp.getProduct_name()+"\"></td>");
            out.println("<td class=\"span6\"><h4>"+ tmp.getProduct_name() +"</h4>"
                    + "<strong>Ordinato in data : </strong>" + tmp.getOrder_date() + "<br>"
                    + "<strong>Ordine : </strong>#"+tmp.getOrder_id()+"<br>"
                    + "<strong>Compratore : </strong>" + tmp.getBuyer_name()+ "</td>");
            out.println("<td>"
                    + "<strong><br>Prezzo: </strong>" + tmp.getPrice() + "$ * ");
            out.println(tmp.getQuantity()+" "+tmp.getUm()+" <br>");
            out.println("<strong>Data: "+ tmp.getOrder_date() +" <br>");
            out.println("--------------------------------------------<br>");
            out.println("<strong>Totale : <span style=\"color:red\">" + tmp.getTotal_price() + "</span></strong>$<br>");
            out.println("<strong>Fattura : </strong><a href=\"" + contextPath + "/"+tmp.getReceipt_url()+"\" >Fattura</a></td></tr>");
            
           
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>");     

    }
    
    
    public void printSellerStorePage(PrintWriter out, ArrayList category_list, ArrayList sell_list  , String message , int type){
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" +  contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/jquery-1.8.2.js\"></script>");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/bootstrap.min.js\"></script>");
    out.println("   <title>My Store</title> </head> <body>");
    
    this.printPageHeaderSeller(out, category_list);
    
    out.println("       <div class=\"span10\">");
    out.println("<h3>I miei prodotti</h3><br>");
    
        
        if(message != null)
             {  
                out.println("<div style=\"text-align:center\">");
                if(type == -1)
                {
                out.println("   <div class=\"alert alert-error fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <h4><p algin=\"center\" class=\"text-error\"> " + message + "</p></h4> ");
                out.println("   </div>");
                }
                else
                {
                out.println("   <div class=\"alert alert-success fade in\">");
                out.println("   <a class=\"close\" data-dismiss=\"alert\" href=\"#\">&times;</a>");
                out.println("   <h4><p algin=\"center\" class=\"text-success\"> " + message + "</p></h4> ");
                out.println("   </div>");
                }     
                out.println("</div>");     
             }  
    
    
    out.println("           <table class=\"table\"> <tbody>");
    
    Iterator iter = sell_list.iterator();
    
    if(sell_list.isEmpty()){
        out.println("<h3>Non hai ancora immesso prodotti</h3>");
    }
          while(iter.hasNext())
          {
          Product tmp = (Product) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImage_url() +"\" width=\"100\" height=\"100\" alt=\""+tmp.getProduct_name()+"\"></td>");
            out.println("<td>"
                    + "<h4>"+ tmp.getProduct_name() +"</h4>"
                    +" <strong>Ordinato in data : </strong>" + tmp.getOrder_date() + "<br>"
                    + "<strong>Prezzo : <span style=\"color:red\">" + tmp.getPrice() + "</span></strong>$");
            out.println("<br><strong>Disponibilità : </strong>" + tmp.getQuantity() +" " + tmp.getUm());
            out.println("<br><p> <small> "+tmp.getDescription() +" </small></p></td></tr>");
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>"); 
    }
    
    
    public void printSellerProductsPage(PrintWriter out, ArrayList category_list , ArrayList products_list , int category_id)
    {
    
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" +  contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/jquery-1.8.2.js\"></script>");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/bootstrap.min.js\"></script>");
    out.println("   <title>Lista Prodotti</title> </head> <body>");

    
    String category_name = this.printProductPageHeaderSeller(out, category_list , category_id);
        
    out.println("       <div class=\"span10\">"); 
    out.println("           <h3>"+ category_name +"</h3>");
        if(products_list.isEmpty()) {
            out.println("<h3>Non ci sono prodotti per questa categoria</h3>");
        }  
    out.println("           <table class=\"table\"> <tbody>");
    Iterator iter = products_list.iterator();
            while(iter.hasNext())
            {
            Product tmp = (Product) iter.next();
            out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/"+ tmp.getImage_url() +"\" width=\"100\" height=\"100\" alt=\""+tmp.getProduct_name()+"\"></td>");
            out.println("<td>"
                    + "<h5>"+ tmp.getProduct_name() +"</h5>"
                    + "</a><strong>Prezzo : <span style=\"color:red\">" + tmp.getPrice() + "</span></strong>$");
            out.println("<br><strong>Disponibilità : </strong>" + tmp.getQuantity() +" " + tmp.getUm());
            out.println("<br><p> <small> "+tmp.getDescription() +" </small></p>");
            }     
    out.println("           </tbody> </table> </div> </div> </div>");
    out.println("   </body>");
    out.println("</html>"); 
    }
    
 
    public void printSellerAddProductPage(PrintWriter out,  ArrayList category_list , File[] listOfFiles){
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" +  contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/jquery-1.8.2.js\"></script>");
    out.println("<script src=\"" + contextPath + "/Bootstrap/js/bootstrap.min.js\"></script>");  
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
    out.println("function validate(form){");
    out.println("   var nome = form.nome.value;"); 
    out.println("   var misura = form.um.value;"); 
    out.println("   var prezzo = form.price.value;"); 
    out.println("   var quantita = form.quantity.value;"); 
    out.println("   var corretto = 1;"); 
    out.println("   if(nome == \"\" || nome == null ){");
    out.println("       document.getElementById(\"error_name\").innerHTML = \"*Errore, il nome non può essere vuoto\"; corretto = 0;}");
    out.println("   else");
    out.println("       document.getElementById(\"error_name\").innerHTML = \"\";");
    out.println("   if(misura == \"\" || misura == null ){");
    out.println("       document.getElementById(\"error_misura\").innerHTML = \"*Errore, il nome non può essere vuoto\"; corretto = 0;}");
    out.println("   else");
    out.println("       document.getElementById(\"error_misura\").innerHTML = \"\";");  
    out.println("   if(prezzo == \"\" || parseInt(prezzo)<0){");
    out.println("       document.getElementById(\"error_prezzo\").innerHTML = \"*Errore, il prezzo non è inserito correttamente\"; corretto = 0;}");
    out.println("   else");
    out.println("       document.getElementById(\"error_prezzo\").innerHTML = \"\";");
    out.println("   if(quantita == \"\" || parseInt(quantita)<0){");
    out.println("       document.getElementById(\"error_quantita\").innerHTML = \"*Errore, la quantità non è inserita correttamente\"; corretto = 0;}");
    out.println("   else");
    out.println("       document.getElementById(\"error_quantita\").innerHTML = \"\";");  
    out.println("   if(corretto == 1) return true; else return false;");
    out.println("    }"); 
    
    out.println(" </script>");     
    out.println("   <title>Aggiunta prodotto</title> </head> <body>");
    
    this.printPageHeaderSeller(out, category_list);
    
       out.println("       <div class=\"span10\">");
   out.println("<div id=\"form_div\">");
       out.println("            <H3>Immetti le informazioni necessarie</H3>");
       out.println("<form class=\"form-horizontal\" action=\"SellerAddController?op=confirm\" onsubmit=\"return validate(this)\"  method=\"post\">");
       out.println("    <div class=\"control-group\">");
       out.println("            <div class=\"controls\">");
       out.println("<a href=\"#\" id=\"select_image\" onClick=\"return false;\">");  
       out.println("<img id=\"image_preview\" src=\"/PrimoProgetto/Images-site/immagine_vuota.jpg\" width=\"150px;\" height=\"150px;\" class=\"img-polaroid\"><br>");
       out.println("</a>");  
       out.println("    </div></div>");  
       out.println("    <div class=\"control-group\">");
       out.println("        <label class=\"control-label\">Nome Prodotto</label>");
       out.println("            <div class=\"controls\">");
       out.println("                <input type=\"text\" placeholder=\"Inserisci un nome prodotto\" name=\"nome\">");
       out.println("                <span class=\"text-error\" id=\"error_name\"></span>");
       out.println("    </div></div>");  						
       out.println("    <div class=\"control-group\">");
       out.println("        <label class=\"control-label\">Descrizione</label>");
       out.println("            <div class=\"controls\">");
       out.println("                <input type=\"text\" placeholder=\"Inserisci una descrizione\" name=\"description\">");
       out.println("    </div></div>");
       out.println("    <div class=\"control-group\">");
       out.println("        <label class=\"control-label\">Unità di Misura</label>");
       out.println("            <div class=\"controls\">");
       out.println("                <input type=\"text\" placeholder=\"Inserisci un unità\" name=\"um\">");
       out.println("                <span class=\"text-error\" id=\"error_misura\"></span>");
       out.println("    </div></div>");
       out.println("    <div class=\"control-group\">");
       out.println("<label class=\"control-label\">Quantità</label>");
       out.println("<div class=\"controls\">");
       out.println("<input type=\"number\" placeholder=\"Inserisci una quantità\"  name=\"quantity\">");
       out.println("                <span class=\"text-error\" id=\"error_quantita\"></span>");
       out.println("    </div></div>");
       out.println("    <div class=\"control-group\">");
       out.println("<label class=\"control-label\">Prezzo</label>");
       out.println("<div class=\"controls\">");
       out.println("<input type=\"number\" placeholder=\"Inserisci un prezzo\" name=\"price\" >");
       out.println("                <span class=\"text-error\" id=\"error_prezzo\"></span>");
       out.println("    </div></div>");
       out.println("    <div class=\"control-group\">");
       out.println("        <label class=\"control-label\">Categorie:</label>");
       out.println("            <div class=\"controls\">");
       out.println("                <select class=\"combobox\" name=\"category\">");
       Iterator iter = category_list.iterator();
       while(iter.hasNext()){
            Category tmp = (Category) iter.next();
            out.println("               <option value=\""+tmp.getId()+"\">"+ tmp.getName() +"</option>");
       }
       out.println("                </select>");
       
       out.println("    </div></div>");
       out.println("                            <input id=\"image_name\" name=\"image_url\" type=\"hidden\" > ");
       out.println("        <div class=\"control-group\">");
       out.println("            <div class=\"controls\">");
       out.println("                <button type=\"submit\" class=\"btn\">Avanti</button>"); 
       out.println("        </div></div>");
       out.println("        </form>");
    out.println("</div>");
       
    out.println("<div  id=\"image_div\" class=\"hide\">");
    out.println("<h3>Seleziona un'immagine</h3>");
    String file_name; 
    for (int i = 0; i < listOfFiles.length; i++) 
            {
                 if (listOfFiles[i].isFile()) 
                 {
                file_name = listOfFiles[i].getName();
                out.println("<a href=\"#\" onClick=\"return changeImage('"+file_name+"');\"> ");
                out.println("<img src=\""+ contextPath + "/Images/" + file_name + "\" width=\"150\" height=\"150\">");
                out.println("</a>");
                 }
            }
    out.println("</div>");
       
       out.println("        </div> </div>");
       out.println("    </body>");
       out.println("  </html>");
    
    }
    

public void printSellerAddConfirmPage(PrintWriter out , ArrayList category_list, Product product)
    {
    out.println("<!DOCTYPE html>");
    out.println("<html><head>");
    out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
    out.println("<link href=\"" + contextPath + "/Bootstrap/css/bootstrap.css\" rel=\"stylesheet\">");   
    out.println("   <title>Conferma ordine</title> </head> <body>");
    
    this.printPageHeaderSeller(out, category_list);
    
       out.println("       <div class=\"span10\">");
       out.println("<h3>Conferma vendita</h3>");
       out.println("           <table class=\"table\"> <tbody>");
       out.println("<tr><td class=\"span3\"><img src=\"" + contextPath + "/Images/"+ product.getImage_url() +"\" width=\"150\" height=\"150\" alt=\""+product.getProduct_name()+"\"></td>");
       out.println("<td><p><h4>"+ product.getProduct_name() + "</h4>");   
       out.println("<small>"+product.getDescription()+"</small><br>");
       out.println("<strong>Prezzo : </strong>"+product.getPrice() + "$<br>");
       out.println("<strong>Pezzi venduti : </strong>"+product.getQuantity() + " " + product.getUm() + "<br>");
       out.println("--------------------------------------------<br></p>");
       out.println("<strong>Prezzo totale : <span style=\"color:red\" id=\"totale\">"+product.getPrice()*product.getQuantity()+"</span></strong>$"); 
       out.println("                  &ensp;<span style=\"color:red\" id=\"messaggio\"></span><br></p>");
       out.println("        <form action=\"SellerAddController?op=response\" method=\"post\">");     
       out.println("                <input type=\"hidden\" name=\"prec_op\" value=\"confirm\">");
       out.println("        <div class=\"control-group\">");
       out.println("            <div class=\"controls\">");
       out.println("                <button class=\"btn\" type=\"submit\">Conferma Vendita</button>");
       out.println("                <a type=\"button\" class=\"btn\" href=\"SellerAddController?op=cancel\">Annulla vendita</a>"); 
       out.println("            </div>");
       out.println("        </div> </td></tr>");
       out.println("           </tbody> </table> </div> </div> </div>");
       out.println("   </body>");
       out.println("</html>"); 
    }
    
    
    
}

