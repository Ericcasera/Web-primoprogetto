/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Listener;

import Managers.DBmanager;
import Managers.HtmlManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.bind.Marshaller.Listener;

/**
 * Web application lifecycle listener.
 *
 * @author Daniel
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            String DBurl , DBdriver;
            DBurl = sce.getServletContext().getInitParameter("DBurl");
            DBdriver = sce.getServletContext().getInitParameter("DBdriver");
            sce.getServletContext().setAttribute("DbManager", new DBmanager(DBurl , DBdriver));
            sce.getServletContext().setAttribute("HtmlManager", new HtmlManager());
        } catch (Exception ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBmanager.shutdown();
    }
}
