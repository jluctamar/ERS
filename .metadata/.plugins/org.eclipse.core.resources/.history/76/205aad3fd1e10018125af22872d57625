package com.revature.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {

	
public static String process(HttpServletRequest request) {
		
		switch(request.getRequestURI()) {
		
		case "/ERS/login.view":
			return "partials/login.html";
		
		case "/ERS/register.view":
			return "partials/register.html";
			
		case "/ERS/home.view":
			return "partials/home.html";
			
		default:
			return null;
			
		}
}
}
