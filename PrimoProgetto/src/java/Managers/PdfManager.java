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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
    
    @SuppressWarnings("empty-statement")
    private String Sha1Name(String fileName) throws Exception{

        
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        FileInputStream     fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        DigestInputStream   dis = new DigestInputStream(bis, sha1);

        // read the file and update the hash calculation
        while (dis.read() != -1);

        // get the hash value as byte array
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
      Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
      Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
      Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
      Font smallRedBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD , BaseColor.RED);
      
      try{
            
      //Titolo
      Paragraph title = new Paragraph("Fattura ordine #"+order_id , titleFont);
      addEmptyLine(title, 3);
      title.setAlignment(Element.ALIGN_CENTER);
      document.add(title);
      
      //Corpo
      Paragraph body = new Paragraph();
      body.add(new Chunk("Ordinato in data    : " , smallBold));
      body.add(new Chunk(""+new java.sql.Date(Calendar.getInstance().getTimeInMillis()) +"\n" , normalFont)); 
      
      body.add(new Chunk("Ordine eseguito da  : " , smallBold));
      body.add(new Chunk(""+ buyer_name +"\n" , normalFont)); 
      
      body.add(new Chunk("Codice prodotto     : #" , smallBold));
      body.add(new Chunk(""+product.getProduct_id()+"\n" , normalFont));
      
      body.add(new Chunk("Nome prodotto       : " , smallBold));
      body.add(new Chunk(""+product.getProduct_name()+"\n" , normalFont));
      
      body.add(new Chunk("Prodotto venduto da : " , smallBold));
      body.add(new Chunk(""+product.getSeller_name()+"\n" , normalFont));
      
      body.add(new Chunk("------------------------------------------------------------------------------\n" , normalFont));
      
      body.add(new Chunk("Quantità acquistata : " , smallBold));
      body.add(new Chunk(""+product.getQuantity()+" " , normalFont));
      body.add(new Chunk(""+product.getUm()+"\n" , normalFont));
      
      body.add(new Chunk("Prezzo              : ", smallBold));
      body.add(new Chunk(""+product.getPrice()+"$\n\n" , normalFont));
      
      body.add(new Chunk("Prezzo totale       : " , smallBold));
      body.add(new Chunk(""+product.getPrice()*product.getQuantity() , smallRedBold));    
      body.add(new Chunk("$\n\n" , normalFont)); 
      
      body.setAlignment(Element.ALIGN_LEFT);
      document.add(body);
      
      }catch(Exception e){
        Logger.getLogger(PdfManager.class.getName()).log(Level.SEVERE, null, e);
      }

    }
    
    
    public String buildReceipt(ServletContext context , Product product , String buyer_name , int order_id){ 
           
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
    
   private static void addEmptyLine(Paragraph paragraph, int number) {
    for (int i = 0; i < number; i++) {
      paragraph.add(new Paragraph(" "));
    }
  }
    
    
    
}
