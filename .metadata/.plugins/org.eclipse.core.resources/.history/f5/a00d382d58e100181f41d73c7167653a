package com.revature;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.EmployeeDAOImpl;
import com.revature.dao.ReimbursementDAOImpl;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;

//contain the main method for the application
public class ERS_Driver {

	public static void main(String[] args) {
		
		ReimbursementDAOImpl test = new ReimbursementDAOImpl();
		List<Reimbursement> record = new ArrayList<>();
		EmployeeDAOImpl emp1 = new EmployeeDAOImpl();
		EmployeeDAOImpl emp2 = new EmployeeDAOImpl();
		Employee emp = new Employee();
		boolean emp3;
		
		//test.createReimbursement(2.0, 1,1, "this is a mock uhh");
		
		
		//Testing the reimbursement get functions
		record = test.mngGetAll();
		for(Reimbursement e : record) {
			System.out.println(e);
		}
		
		//Testing Employee get functions
		emp = emp1.getEmployee(2);
		emp3 = emp2.checkEmployee("jSmith","pasord");
		
		System.out.println(emp);
		System.out.println(emp3);
		
		
		
		
		
	}
	
}
