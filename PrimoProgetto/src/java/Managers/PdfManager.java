/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Product;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
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
    
    
    public String buildPdf(ServletContext context , Product product){ 
           
        String path = context.getRealPath("Receipts/"+product.getProduct_id()+"_"+product.getProduct_name()+".pdf");
        Document document = new Document();
        
        try{
        
            PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Paragraph p = new Paragraph("Ciao sono il prodotto" + product.getProduct_name() + " venduto da " +product.getSeller_name() , FontFactory.getFont(FontFactory.HELVETICA,18));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
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
        return null;
        
        }                    
    }

    public String savePdf(){ 
        //Questo metodo passati i dati , richiama build pdf e salva il pdf con codice sha-1 
        //E ritorna il codice cosicche si possa salvarlo nel database
        return null;
    }
    

    
    
    
    
}
