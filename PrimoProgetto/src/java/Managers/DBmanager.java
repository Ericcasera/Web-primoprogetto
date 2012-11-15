/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Category;
import Beans.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Daniel
 */
public class DBmanager {
 
private transient Connection con;
    
    public DBmanager(String dburl , String driver){
                try {
                    
                Class.forName(driver, true, getClass().getClassLoader());
                con = DriverManager.getConnection(dburl); 
                
                } catch(Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null ,ex);
                }        
    }
    
    public static void shutdown() 
          {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }
            catch (Exception ex){
            }
          } 
    
    
    public User Autentication(String username , String password)
        {
            String query = "Select * from users where username = ? and password = ? ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if(rs.next())
                {
                    User x = new User();
                    x.setUsername(username);
                    x.setPassword(password);
                    x.setRole(rs.getInt("role"));
                    rs.close(); 
                    return x;
                }       
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{
              
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
        return null;
     
     }
    
    public ArrayList queryCategory(ServletContext context){
    
            String query = "Select * from category";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Category tmp;
            
            try {     
            stm = con.prepareStatement(query);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Category();
                    tmp.setId(rs.getInt("ID"));
                    tmp.setName(rs.getString("NAME"));
                    tmp.setDescription(rs.getString("DESCRIPTION"));
                    tmp.setImageURL(context.getRealPath("Images/" + (rs.getString("IMAGE_URL"))));
                    lista.add(tmp);
                } 
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
        }
        finally {
                try{
                   rs.close();            
                   stm.close();   
                 }
                 catch (Exception ex) {
                     Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null , ex);
                 }
            }
            
        return lista;
    }
    
    
    
       
}
