
package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Logger log = LogManager.getLogger(LoginServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		log.info("Request sent to LoginServlet.doPost()");
	
		//create a new Services object from the Employee Services
		EmployeeServices userService = new EmployeeServices();

		//Create mapper in order to read STRINGIFIED JSON Object from 
		ObjectMapper mapper = new ObjectMapper();

		String[] userCredentials = mapper.readValue(request.getInputStream(), String[].class);

		String username = userCredentials[0];
		String password = userCredentials[1];

		//Employee who is logging in is mapped to the 'authUser' object which is then returned to the front end via JSON
		Employee tempEmpObject = userService.getEmployee(username, password);
		Employee authUser = new Employee();
		if (tempEmpObject ==  null ) {
			authUser = null;
		} else {
			authUser = Employee.duplicate(tempEmpObject);
			authUser.setPass("***************");
			HttpSession session = request.getSession();
			session.setAttribute("user", authUser);
		}


		PrintWriter pw = response.getWriter();
		response.setContentType("application/json");
		String authUserJSON = mapper.writeValueAsString(authUser);
		pw.write(authUserJSON);

	}
}
