/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Managers;

import Beans.Category;
import Beans.Order;
import Beans.Product;
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
                    x.setId(rs.getInt("id"));
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
            rs.close(); 
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
            
        return lista;
    }
    
        public ArrayList queryProducts(ServletContext context , int category_id){
    
            String query = "Select * from products where category_id = ?";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Product tmp;
            
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, category_id);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Product();
                    tmp.setId(rs.getInt("id"));
                    tmp.setSeller_id(rs.getInt("seller_id"));
                    tmp.setCategory_id(category_id);
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setName(rs.getString("name"));
                    tmp.setDescription(rs.getString("description"));
                    tmp.setImage_url(rs.getString("image_url"));
                    tmp.setUm(rs.getString("um"));
                    tmp.setDate_order(rs.getDate("date_order"));
                    lista.add(tmp);
                    
                } 
                   rs.close(); 
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
            
        return lista;
    }
        
        
    public ArrayList queryBuyerOrders(ServletContext context , int buyer_id){
    
            String query = "Select o.id as id , o.UM as um , o.QUANTITY as quantity , o.PRICE as price , o.TOTAL_PRICE as total_price , o.DATE_ORDER as order_date , o.RECEIPT_URL as receipt_url, NAME as name " 
                    + "from orders o join products p on o.PRODUCT_ID = p.ID " 
                    + "where o.BUYER_ID = ?";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Order tmp;
            
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, buyer_id);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Order();
                    tmp.setName(rs.getString("name"));
                    tmp.setOrder_date(rs.getDate("order_date"));
                    tmp.setOrder_id(rs.getInt("id"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setTotal_price(rs.getInt("total_price"));
                    tmp.setUm(rs.getString("um"));
                    lista.add(tmp);  
                } 
                   rs.close(); 
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
            
        return lista;
    }
    
    
    
    
    
       
}
