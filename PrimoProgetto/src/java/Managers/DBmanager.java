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
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Daniel
 */
public class DBmanager {
 
private transient Connection con;
private String contextPath;
private ServletContext context;
    
    public DBmanager(String dburl , String driver , ServletContext context){
                try {
                    
                Class.forName(driver, true, getClass().getClassLoader());
                con = DriverManager.getConnection(dburl); 
                contextPath = context.getContextPath();
                this.context = context;
                
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
    
    public ArrayList queryCategory(){
    
            String query = "Select * from category order by name";
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
                    tmp.setImageURL("Images/" + (rs.getString("IMAGE_URL")));
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
    
        public ArrayList queryProductsList(int category_id){
    
            String query = "Select * from products where category_id = ? order by name";
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
                    tmp.setProduct_id(rs.getInt("id"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setCategory_id(category_id);
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setProduct_name(rs.getString("name"));
                    tmp.setDescription(rs.getString("description"));
                    tmp.setImage_url("Images/" + rs.getString("image_url"));
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
        
        
    public ArrayList queryBuyerOrders(int buyer_id){
    
            String query = "Select o.id as order_id , o.UM as um , o.QUANTITY as quantity , o.PRICE as price ,"
                    + "o.TOTAL_PRICE as total_price , o.DATE_ORDER as order_date , o.RECEIPT_URL as receipt_url, "
                    + "p.NAME as product_name , p.image_url as image_url , u.username as seller_name " 
                    + "from (orders o join products p on o.PRODUCT_ID = p.ID) join users u on seller_id = u.id " 
                    + "where o.BUYER_ID = ?"
                    + "order by order_date desc";
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
                    tmp.setOrder_id(rs.getInt("order_id"));
                    tmp.setSeller_name(rs.getString("seller_name"));
                    tmp.setProduct_name(rs.getString("product_name"));
                    tmp.setOrder_date(rs.getDate("order_date"));
                    tmp.setReceipt_url("Receipts/" + rs.getString("receipt_url"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setTotal_price(rs.getInt("total_price"));
                    tmp.setImage_url("Images/" + rs.getString("image_url"));
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
    
    public Product queryProduct(int product_id)
    {
           
        String query = " Select p.id as product_id , category_id ,name , description , image_url , um , quantity , price , username"
                     + " from products p join users u on u.ID = p.SELLER_ID "
                     + " where p.ID = ? ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            Product tmp = null;
            
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, product_id);
            rs = stm.executeQuery();
            
            if(rs.next())
                {
                    tmp = new Product();
                    tmp.setCategory_id(rs.getInt("category_id"));
                    tmp.setDescription(rs.getString("description"));
                    tmp.setImage_url("Images/" + rs.getString("image_url"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setProduct_id(product_id);
                    tmp.setProduct_name(rs.getString("name"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setSeller_name(rs.getString("username"));
                    tmp.setUm(rs.getString("um"));
                } 
                   rs.close(); 
                   return tmp;
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
    
    public boolean queryUpdateProdcuts(Product product){
        String query = " UPDATE products SET quantity = quantity - ? where id = ? ";   
        PreparedStatement stm = null ;
        
    try {  
            
        stm = con.prepareStatement(query);
        stm.setInt(1, product.getQuantity());
        stm.setInt(2, product.getProduct_id());
        
        int result = stm.executeUpdate();
        if(result == 0) {
            return false;
        }
        else {
            return true;
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
        
        return false;
   }
    
   public int queryInsertBuyOrder(Product product , int buyer_id){
        
            String query = " Insert into orders (buyer_id , product_id , um , quantity , price , total_price , date_order) "
                             + "values (? , ? , ? , ? , ? , ? , ?)";
            PreparedStatement stm = null ;
           
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, buyer_id);
            stm.setInt(2, product.getProduct_id());
            stm.setString(3, product.getUm());
            stm.setInt(4, product.getQuantity());
            stm.setInt(5, product.getPrice()); 
            stm.setInt(6, product.getPrice() * product.getQuantity());
            stm.setDate(7, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            int result = stm.executeUpdate();
            
            if(result == 0) {
                    return -1;
                }
            else {
                    return queryLastOrder(buyer_id);
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
            
            return -1;
   }
   
   private int queryLastOrder(int buyer_id)
        {
            String query = "Select max(id) as last_order from orders where buyer_id = ? ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            
            try {     
            stm = con.prepareStatement(query);
            stm.setInt(1, buyer_id);
            rs = stm.executeQuery();
            if(rs.next())
                {
                    return Integer.parseInt(rs.getString("last_order"));
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
        return -1;    
     }
   
 public void queryInsertReceipt(int order_id , String receipt_path)
     { 
            String query = " Update orders set Receipt_url = ? where id = ? " ;
            PreparedStatement stm = null ;
           
            try {        
            stm = con.prepareStatement(query);
            stm.setString(1, receipt_path);
            stm.setInt(2, order_id);
            stm.executeUpdate();        
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
   } 
   
   
    public ArrayList querySellerOrders(int seller_id){
    
            String query = "Select o.id as order_id , o.UM as um , o.QUANTITY as quantity , o.PRICE as price ,"
                    + "o.TOTAL_PRICE as total_price , o.DATE_ORDER as order_date , o.RECEIPT_URL as receipt_url, "
                    + "p.NAME as product_name , p.image_url as image_url , u.username as seller_name, us.username as buyer_name  " 
                    + "from ((orders o join products p on o.PRODUCT_ID = p.ID) join users u on seller_id = u.id) join users us on buyer_id = us.id " 
                    + "where p.seller_id = ?"
                    + "order by order_date desc";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Order tmp;
            
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, seller_id);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Order();
                    tmp.setOrder_id(rs.getInt("order_id"));
                    tmp.setSeller_name(rs.getString("seller_name"));
                    tmp.setBuyer_name(rs.getString("buyer_name"));
                    tmp.setProduct_name(rs.getString("product_name"));
                    tmp.setOrder_date(rs.getDate("order_date"));
                    tmp.setReceipt_url("Receipts/" + rs.getString("receipt_url"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setTotal_price(rs.getInt("total_price"));
                    tmp.setImage_url("Images/" + rs.getString("image_url"));
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
   

public boolean queryInsertNewProduct(Product product, int seller_id){
        
            String query = " Insert into products (seller_id , category_id , description , NAME , image_url , um , quantity , price, date_order) "
                             + "values (? , ? , ? , ? , ? , ? , ? , ?, ?)";
            
        PreparedStatement stm = null ;
           
            try {        
            stm = con.prepareStatement(query);

            stm.setInt(1, seller_id);
            stm.setInt(2, product.getCategory_id());
            stm.setString(3, product.getDescription());
            stm.setString(4, product.getProduct_name());
            stm.setString(5, product.getImage_url()); 
            stm.setString(6, product.getUm());
            stm.setInt(7, product.getQuantity());
            stm.setInt(8, product.getPrice());
            stm.setDate(9, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            int result = stm.executeUpdate();
            
            if(result == 0) {
                    return false;
                }
            else {
                    return true;
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
        return false;
    }


   public ArrayList querySell(int Seller_id){
    
            String query = "Select * from products where seller_id = ? order by date_order desc ";
            PreparedStatement stm = null ;
            ResultSet rs = null;
            ArrayList lista = new ArrayList(40);
            Product tmp;
            
            try {        
            stm = con.prepareStatement(query);
            stm.setInt(1, Seller_id);
            rs = stm.executeQuery();
            
            while(rs.next())
                {
                    tmp = new Product();
                    tmp.setProduct_id(rs.getInt("id"));
                    tmp.setPrice(rs.getInt("price"));
                    tmp.setOrder_date(rs.getDate("date_order"));
                    tmp.setCategory_id(rs.getInt("category_id"));
                    tmp.setQuantity(rs.getInt("quantity"));
                    tmp.setProduct_name(rs.getString("name"));
                    tmp.setDescription(rs.getString("description"));
                    tmp.setImage_url("Images/" + rs.getString("image_url"));
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
