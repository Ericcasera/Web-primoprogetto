/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Product;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;



/**
 *
 * @author Daniel
 */
public class PdfManager {
    
    private ServletContext context;
    private String contextPath;
    
    public PdfManager(ServletContext context){
        this.context = context;
        this.contextPath = context.getContextPath();  
    }
    
    @SuppressWarnings("empty-statement")
    private String Sha1Name(String fileName) throws Exception{

        
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        FileInputStream     fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DigestInputStream   dis = new DigestInputStream(bis, sha1);

        while (dis.read() != -1);

        byte[] hash = sha1.digest();

        fis.close();
        bis.close();
        dis.close();      
        return byteArray2Hex(hash);
    } 
    
    private String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
    
    private void buildPdf(Document document , Product product , String buyer_name , int order_id)
    {  
      Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 13, Font.BOLD);
      Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.NORMAL);
      Font smallBold = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);
      Font smallRedBold = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.RED);
      
      LineSeparator separator = new LineSeparator(0.5f, 100, BaseColor.DARK_GRAY, Element.ALIGN_CENTER, -2);
      
      try{
          
      //Logo
      Image logo = Image.getInstance(context.getRealPath("/Images-site/logo_login.png"));
      logo.scalePercent(50f);
      document.add(logo);
      document.add(new Chunk(separator));
      
      //Corpo
      Paragraph body = new Paragraph();
      
      Image image = Image.getInstance(context.getRealPath(product.getImage_url()));
      image.scaleToFit(200f,200f);
      image.setAlignment(Image.TEXTWRAP);
      body.add(image);
      
      
      //Titolo
      body.add(new Chunk("\n  Fattura ordine #"+order_id , titleFont));
      body.add(Chunk.NEWLINE);
      
      //Dati
      body.add(new Chunk("\n  Ordinato in data        : " , smallBold));
      body.add(new Chunk(""+new java.sql.Date(Calendar.getInstance().getTimeInMillis()) +"\n" , normalFont)); 
      
      body.add(new Chunk("  Ordine eseguito da   : " , smallBold));
      body.add(new Chunk(""+ buyer_name +"\n" , normalFont)); 
      
      body.add(new Chunk("  Codice prodotto        : #" , smallBold));
      body.add(new Chunk(""+product.getProduct_id()+"\n" , normalFont));
      
      body.add(new Chunk("  Nome prodotto          : " , smallBold));
      body.add(new Chunk(""+product.getProduct_name()+"\n" , normalFont));
      
      body.add(new Chunk("  Prodotto venduto da : " , smallBold));
      body.add(new Chunk(""+product.getSeller_name()+"\n" , normalFont));
      
      //Prezzo
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      body.add(Chunk.NEWLINE);
      
      body.add(new Chunk("Riepilogo:\n" , titleFont));
      
      body.add(Chunk.NEWLINE);
      
      body.add(new Chunk("Quantità acquistata : " , smallBold));
      
      body.add(new Chunk(""+product.getQuantity()+" " , normalFont));
      body.add(new Chunk(""+product.getUm()+"\n" , normalFont));
      
      body.add(new Chunk("Prezzo                       : ", smallBold));
      body.add(new Chunk(""+product.getPrice()+" $\n" , normalFont));
      
      body.add(new Chunk(separator));
      body.add(new Chunk("\nPrezzo totale            : " , smallBold));
      body.add(new Chunk(""+product.getPrice()*product.getQuantity()+" $" , smallRedBold)); 
      
      document.add(body);
      
      }catch(Exception e){
        Logger.getLogger(PdfManager.class.getName()).log(Level.SEVERE, null, e);
      }

    }
    
    
    public String buildReceipt(Product product , String buyer_name , int order_id){ 
           
        String path = context.getRealPath("Receipts/"+product.getProduct_id()+"_"+product.getProduct_name()+".pdf");
        Document document = new Document();
        
        try{
        
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        
        buildPdf(document , product , buyer_name , order_id);
        
        document.close();  
        String sha1 = Sha1Name(path);
      
        if(sha1 != null)
        {
        File f = new File(path); 
        String new_path = context.getRealPath("Receipts/" + sha1 + ".pdf");    
        f.renameTo(new File(new_path));      
        }
              
        return sha1 + ".pdf";
        
        }catch(Exception e){         
        Logger.getLogger(PdfManager.class.getName()).log(Level.SEVERE, null, e);
        return null; }                    
    }
      
}
