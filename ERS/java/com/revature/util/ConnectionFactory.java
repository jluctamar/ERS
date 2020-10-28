package com.revature.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Class handle the JDBC logic
public class ConnectionFactory {

	//declare/ initialize class variables
	private static ConnectionFactory cf;
	private static boolean build = true;
	Logger log = LogManager.getLogger(ConnectionFactory.class);
	
	
	private ConnectionFactory() {
		build = false;
	}
	
	public static synchronized ConnectionFactory getInstance() {
		return (build) ? cf = new ConnectionFactory() : cf;
	}
	
	//establishes connection to the database
	public Connection getConnection()  {
		Connection conn = null;
		Properties prop = new Properties();
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// Load the properties file keys/values into the Properties object
			prop.load(new FileReader("C:/Users/Jeffl/Workspace/Coding Projects/Expense Reimbursement Application/ERS/src/main/resources/Application.Properties"));

			// Get a connection from the DriverManager
			conn = DriverManager.getConnection(
					prop.getProperty("url"), 
					prop.getProperty("username"), 
					prop.getProperty("password"));
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
}
}
