package com.revature;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.EmployeeDAOImpl;
import com.revature.dao.ReimbursementDAOImpl;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;

//contain the main method for the application
public class ERS_Driver {

	public static void main(String[] args) {
		
		ReimbursementDAOImpl test = new ReimbursementDAOImpl();
		List<Reimbursement> record = new ArrayList<>();
		EmployeeDAOImpl emp1 = new EmployeeDAOImpl();
		EmployeeDAOImpl emp2 = new EmployeeDAOImpl();
		Employee emp = new Employee();
		boolean emp3;
		
		
		ReimbursementService fill = new ReimbursementService();
		

		fill.createReimbursement(24, 4, 4, "office supplies");
		fill.createReimbursement(4, 4, 4, "office Coffee Machine");
		fill.createReimbursement(1000, 4, 4, "Sleep");

		fill.createReimbursement(140, 3, 4, "Spa Treatment");
		
		fill.createReimbursement(400, 2, 4, "TypeWriter");
		fill.createReimbursement(350, 2, 4, "Particle Accelerator");
		fill.createReimbursement(20000, 2, 4, "SuperComputer");
		fill.createReimbursement(300, 2, 4, "SmartBoard");
		
		
		
		
		
	}
	
}
