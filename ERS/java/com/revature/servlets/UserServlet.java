
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Employee;
import com.revature.services.EmployeeServices;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = LogManager.getLogger(UserServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("Request sent to UserServlet.doGet() - get all users");

		// create a new Services object from the Employee Services
		EmployeeServices userService = new EmployeeServices();
		List<Employee> employeesList = new ArrayList<Employee>();
		ObjectMapper mapper = new ObjectMapper();

		employeesList = userService.getAllEmployees();

		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		String employeesListJSON = mapper.writeValueAsString(employeesList);
		pw.write(employeesListJSON);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info("Request sent to UserServlet.doPost() - create user");

		// create a new Services object from the Employee Services
		EmployeeServices userService = new EmployeeServices();

		// Create mapper in order to read STRINGIFIED JSON Object from
		ObjectMapper mapper = new ObjectMapper();

		Map<String, String> userCredentials = new HashMap<String, String>();
		String[] userInput = mapper.readValue(request.getInputStream(), String[].class);

		userCredentials.put("firstname", userInput[0]);
		userCredentials.put("lastname", userInput[1]);
		userCredentials.put("email", userInput[2]);
		userCredentials.put("signUpUsername", userInput[3]);
		userCredentials.put("signUpPassword", userInput[4]);

		// Make a call to the user service to create the user
		Employee createdUser = userService.createEmployee(userCredentials);
		if (createdUser != null) {
			createdUser.setPass("***************");
		}

		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		String createdUserJSON = mapper.writeValueAsString(createdUser);
		pw.write(createdUserJSON);

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("Request sent to UserServlet.doPut() - Change a users Role");

		// create a new Services object from the Employee Services
		EmployeeServices userService = new EmployeeServices();

		// Create mapper in order to read STRINGIFIED JSON Object from
		ObjectMapper mapper = new ObjectMapper();
		// int userId = Integer.parseInt(mapper.readValue(request.getInputStream(), String.class));
		String[] userInput = mapper.readValue(request.getInputStream(), String[].class);
		
		System.out.println("User input 1: " + userInput[0]);
		System.out.println("User input 2: " + userInput[1]);
		userService.updateEmployeeRole( Integer.parseInt(userInput[0]), Integer.parseInt(userInput[1])); 
		
	}
}
