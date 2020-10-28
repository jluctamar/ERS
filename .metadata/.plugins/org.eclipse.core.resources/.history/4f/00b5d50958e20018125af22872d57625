package com.revature.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {

	public static String process(HttpServletRequest request) {
		System.out.println("In requestViewHelpler: incoming string: ");
		System.out.println(request.getRequestURI());
		
		switch (request.getRequestURI()) {

		case "/ERS/login.view":
			return "partials/login.html";

		case "/ERS/reimbursement.view":
			return "partials/Reimbursement.html";

		case "/ERS/create.view":
			return "partials/createReimbursement.html";

		default:
			return null;

		}
	}
}
