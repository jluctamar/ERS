package com.revature.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RequestViewHelper {

	static Logger log = LogManager.getLogger(RequestViewHelper.class);
	
	public static String process(HttpServletRequest request) {
		log.info("In requestViewHelpler: incoming string: ");
		log.info(request.getRequestURI());
		
		
		switch (request.getRequestURI()) {

		case "/ERS/login.view":
			return "partials/login.html";

		case "/ERS/reimbursement.view":
			return "partials/Reimbursement.html";

		case "/ERS/create.view":
			return "partials/createReimbursement.html";
			
		case "/ERS/profile.view":
			return "partials/profile.html";

		default:
			return null;

		}
	}
}
