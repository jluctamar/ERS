package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.servlets.ReimbursementServlet;
import com.revature.util.ConnectionFactory;

//Contain the implemented Data Access Methods {CRUD(Create.Read.Update.Delete) methods}
public class EmployeeDAOImpl implements EmployeeDAO {

	Logger log = LogManager.getLogger(EmployeeDAOImpl.class);

	@Override
	public Employee getEmployee(int id) {

		// constructs an employee object based on their UserID
		Employee user = new Employee();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM EMPLOYEES WHERE User_ID = ?";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			// loop through each row, assigning each table field to is corresponding object
			// field
			while (rs.next()) {

				user.setEmployeeID(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPass(rs.getString(3));
				user.setFirst_Name(rs.getString(4));
				user.setLast_Name(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setUserRoleID(rs.getInt(7));
			}

		} catch (SQLException sql) {
			log.error("getEmployee function");
			sql.printStackTrace();
		}

		return user; // should return the constructed employee(user) object
	}

	@Override
	public Employee getEmployee(String username, String password) {

		// constructs an employee object based on client-side login credentials
		Employee user = new Employee();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM EMPLOYEES WHERE (Username = ? AND pass = ?)";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			// loop through each row, assigning each table field to is corresponding object
			// field
			while (rs.next()) {
				user = construstEmployee(rs);
			}

		} catch (SQLException sql) {
			log.error("getEmployee by userN/pass function");
			sql.printStackTrace();
		}

		return user; // should return the constructed employee(user) object
	}

	@Override
	public List<Employee> getAllEmployees() {

		List<Employee> usersList =  new ArrayList<Employee>();  
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM EMPLOYEES";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				usersList.add(construstEmployee(rs));
			}

		} catch (SQLException sql) {
			log.error("getAllEmployees Error");
			sql.printStackTrace();
		}
        
		return usersList;
	}
	
	
	
	@Override
	public void updateEmpRole(int id, int roleId) {
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) { 
			
			// Construct & execute the corresponding SELECT statement
			String sql = "UPDATE EMPLOYEES SET User_Role_ID = ? WHERE User_ID = ? ";
			
			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setInt(1, roleId);
			pstmt.setInt(2, id);
			
			//Execute the command
			ResultSet rs = pstmt.executeQuery();
			
		} catch (SQLException sql) {
			log.error("updateEmpRole function");
			sql.printStackTrace();
		}
	}

	@Override
	public boolean checkEmployee(String username, String password) {

		// constructs an employee object based on client-side login credentials
		Employee user = new Employee();
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM EMPLOYEES WHERE (Username = ? AND pass = ?)";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setString(1, username);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();

			// check to see credentials match with the database
			int count = 0;
			while (rs.next()) {
				++count;
			}
			if (count == 1) {
				log.info("Username Exists");
				return true;
			} else {
				return false;
			}
		} catch (SQLException sql) {
			log.error("checkEmployee function");
			sql.printStackTrace();
		}

		return false;
	}

	@Override
	public void createNewUser(Map newUser) { /* NOTE: all new employees are entered as regular employees(Not Admins) */

		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "INSERT INTO EMPLOYEES(Username, Pass, First_Name, Last_Name , Email, User_Role_ID)"
					+ "     VALUES (?, ?, ?, ?, ?, ? )";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setString(1, newUser.get("signUpUsername").toString());
			pstmt.setString(2, newUser.get("signUpPassword").toString());
			pstmt.setString(3, newUser.get("firstname").toString());
			pstmt.setString(4, newUser.get("lastname").toString());
			pstmt.setString(5, newUser.get("email").toString());
			pstmt.setString(6, "1"); // Hard Coded to regular Employee

			ResultSet rs = pstmt.executeQuery();

		} catch (SQLException sql) {
			log.error("createEmployee function");
			sql.printStackTrace();
		}

	}
	
	
	
	public Employee construstEmployee(ResultSet rs) throws SQLException {
		Employee user = new Employee();
		
		user.setEmployeeID(rs.getInt(1));
		user.setUsername(rs.getString(2));
		user.setPass(rs.getString(3));
		user.setFirst_Name(rs.getString(4));
		user.setLast_Name(rs.getString(5));
		user.setEmail(rs.getString(6));
		user.setUserRoleID(rs.getInt(7));
		
		return user; 
	}

}
