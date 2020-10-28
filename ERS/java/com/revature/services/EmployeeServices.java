package com.revature.services;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.dao.EmployeeDAOImpl;
import com.revature.models.Employee;
import com.revature.util.RequestViewHelper;

//Service class to access the DAO methods 
public class EmployeeServices {

	private EmployeeDAOImpl empDAO = new EmployeeDAOImpl();
	Logger log = LogManager.getLogger(EmployeeServices.class);

	public Employee getEmployee(int Id) {
		return empDAO.getEmployee(Id);
	}

	// Checks credentials against the database user table returns Employees info if
	// valid
	public Employee getEmployee(String username, String password) {

		// Check if the employees credentials are valid
		if (validCredentials(username, password)) {
			return empDAO.getEmployee(username, password);
		} else {
			log.warn("Invalid Credentials, user not found.");
			return null;
		}
	}

	public List<Employee> getAllEmployees() {
		List<Employee> empList = empDAO.getAllEmployees();
		
		// Change the value of each employees password to maintain privacy
        for(int i=0; i < empList.size(); i++){
           empList.get(i).setPass("**********");
        }
		
		return empList;
	}

	public void updateEmployeeRole(int userId, int roleId) {
		empDAO.updateEmpRole(userId, roleId);
	}
	
	
	// validates employees credentials
	public boolean validCredentials(String username, String password) {
		return empDAO.checkEmployee(username, password);
	}

	// OPTIMIZE: Currently this method makes 3 separate call to the database
	public Employee createEmployee(Map newUser) {
		Employee createdUser = null;
		// check the user database to see if the credentials presently exist
		if (!empDAO.checkEmployee(newUser.get("signUpUsername").toString(), newUser.get("signUpPassword").toString())) {
			empDAO.createNewUser(newUser);
			createdUser = empDAO.getEmployee(newUser.get("signUpUsername").toString(),
					newUser.get("signUpPassword").toString());
		}

		return createdUser;
	}
}
