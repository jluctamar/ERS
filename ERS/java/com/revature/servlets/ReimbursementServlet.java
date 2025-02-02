package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.services.EmployeeServices;
import com.revature.services.ReimbursementService;

@WebServlet("/reimburse")
public class ReimbursementServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	Logger log = LogManager.getLogger(ReimbursementServlet.class);
	
	//viewing of reimbursement records.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		log.info("Request sent to ReimbursementServlet, doPost()");
		//create the appropriate service objects
		ReimbursementService reimburseService = new ReimbursementService();
		EmployeeServices employeeService = new EmployeeServices();
		List<Reimbursement> reimburseRecords = new ArrayList<>();
		
		
		//map the Stringified JSON to an array 
		ObjectMapper mapper = new ObjectMapper();
		String[] userFields= mapper.readValue(request.getInputStream(), String[].class);
		int userID = Integer.parseInt(userFields[0]) ;
		int userRole = Integer.parseInt(userFields[1]);
		int statusID = Integer.parseInt(userFields[2]);
		Employee user = Employee.duplicate(employeeService.getEmployee(userID));
		
		if (userRole == 2)//Checks for manager 
			switch (statusID) {
			case 0: reimburseRecords = reimburseService.mngGetAll();
					break; //returns all reimbursements
			case 1:
				reimburseRecords = reimburseService.mngGetPending();
				break; // returns users pending reimbursements
			case 2:
				reimburseRecords = reimburseService.mngGetDeclined();
				break; // returns users declined reimbursements
			case 3:
				reimburseRecords = reimburseService.mngGetApproved();
				break; // returns users approved reimbursements
			default:
				reimburseRecords = reimburseService.mngGetAll();
				break;
			}else { 
				
				switch (statusID) {
				case 0: reimburseRecords = reimburseService.empGetAll(userID);
					break;
				case 1:
					reimburseRecords = reimburseService.empGetPending(userID);
					break; // returns users pending reimbursements
				case 2:
					reimburseRecords = reimburseService.empGetDeclined(userID);
					break; // returns users declined reimbursements
				case 3:
					reimburseRecords = reimburseService.empGetApproved(userID);
					break; // returns users approved reimbursements
				default:
					reimburseRecords = reimburseService.empGetAll(userID);
					break;			
			}
		}
		
		//return a JSON object that represents the list of records
		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		String recordsJSON = mapper.writeValueAsString(reimburseRecords);
		pw.write(recordsJSON);
	}
	
	//creation of new reimbursement records
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse resposnse) throws ServletException, IOException{

		log.info("Request sent to ReimbursementServlet, doPut()");
		// create appropriate service objects
		ReimbursementService reimburseService = new ReimbursementService();
		EmployeeServices employeeService = new EmployeeServices();

	
		// map incoming JSON information to an array
		ObjectMapper mapper = new ObjectMapper();		
		String[] userFields = mapper.readValue(request.getInputStream(), String[].class);
		
		//extract relevant information
		Double amount = Double.parseDouble(userFields[0]);		
		int userID = Integer.parseInt(userFields[1]);
		int reimbType = Integer.parseInt(userFields[2]);
		String description = userFields[3];
		
		Employee user = Employee.duplicate(employeeService.getEmployee(userID));

		//creates reimbursement record and inserts it into the table	
		reimburseService.createReimbursement(amount, userID, reimbType, description);
	}

}
