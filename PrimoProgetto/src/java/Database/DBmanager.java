/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
}
