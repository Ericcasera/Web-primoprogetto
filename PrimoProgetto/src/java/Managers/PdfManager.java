/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.servlet.ServletContext;



/**
 *
 * @author Daniel
 */
public class PdfManager {
    
    public void buildPdf(ServletContext context){ //Questo metodo dati i dati di un ordine deve costruire il pdf 
        
        String path = context.getRealPath("Receipts/Test.pdf");
        try{
        Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();
        Paragraph p = new Paragraph("This is not an helloworld!", FontFactory.getFont(FontFactory.HELVETICA,18));
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        Paragraph p1 = new Paragraph(new Chunk( "This is my first paragraph. ", FontFactory.getFont(FontFactory.HELVETICA, 10)));
p1.add("The leading of this paragraph is calculated automagically. ");
p1.add("The default leading is 1.5 times the fontsize. ");
p1.add(new Chunk("You can add chunks "));
p1.add(new Phrase("or you can add phrases. "));
p1.add(new Phrase("Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.", FontFactory.getFont(FontFactory.HELVETICA, 18)));
document.add(p1);
        Image jpg = Image.getInstance(context.getRealPath("Images/plasmarifle.jpg")); 
        jpg.scaleAbsolute(200, 200);
        document.add(jpg);
        document.close();
        }catch(Exception e){}
        
    }

    public String savePdf(){ 
        //Questo metodo passati i dati , richiama build pdf e salva il pdf con codice sha-1 
        //E ritorna il codice cosicche si possa salvarlo nel database
        return null;
    }
    
    public String searchPdf(){
        //Dato un sha-1 ritorna il path corrispondente al pdf cercato 
        return null;
    }
    
    
    
    
}
