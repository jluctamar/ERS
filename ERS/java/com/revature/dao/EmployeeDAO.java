package com.revature.dao;

import java.util.List;
import java.util.Map;

import com.revature.models.Employee;

// Interface containing all (unimplemented) methods that will be used to access/manipulate employee table
// {CRUD(Create.Read.Update.Delete) methods}
public interface EmployeeDAO {


	public Employee getEmployee(int id);//constructs an employee object based on their UserID
	public Employee getEmployee(String username, String password);//constructs an employee object based on client-side login credentials
	public List<Employee> getAllEmployees();
	public void updateEmpRole(int id, int roleId); // Update employee role
	public boolean checkEmployee(String username, String password);
	public void createNewUser(Map newUser);

}
