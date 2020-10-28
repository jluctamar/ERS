package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.services.EmployeeServices;
import com.revature.services.ReimbursementService;

@WebServlet("/updateReimbursement")
public class UpdateReimbursementServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = LogManager.getLogger(UpdateReimbursementServlet.class);
	//updating of reimbursement records(manager exclusive)
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		log.info("Request sent to UpdateReimbursementServlet, doPost()");
		
		// create appropriate service objects
		ReimbursementService reimburseService = new ReimbursementService();
		EmployeeServices employeeService = new EmployeeServices();

		// map incoming JSON information to an array
		ObjectMapper mapper = new ObjectMapper();
		String[] userFields = mapper.readValue(request.getInputStream(), String[].class);
		int userID = Integer.parseInt(userFields[0]);
		int reimbID = Integer.parseInt(userFields[1]);
		int status = Integer.parseInt(userFields[2]);
		Employee manager = Employee.duplicate(employeeService.getEmployee(userID));
		
		reimburseService.updateReimbursement(manager, reimbID, status);
	}
}
