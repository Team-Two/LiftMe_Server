package basic.servlets;

import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import mysql.MySQLAccess;

public class OnServerStartupListener implements ServletContextListener {

	  @Override
	  public void contextDestroyed(ServletContextEvent arg0) {
	    //Notification that the servlet context is about to be shut down.   
	  }

	  @Override
	  public void contextInitialized(ServletContextEvent arg0) {
	    // do all the tasks that you need to perform just after the server starts

		  System.out.println("asdfasdfasdfasdfasfasdf");
		  
		  MySQLAccess msa = new MySQLAccess();
		  try {
			msa.createTables();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }

	}