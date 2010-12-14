package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloWorldServlet extends BasicHttpServlet{
	
	@Inject
	public HelloWorldServlet(ServletOperations operations) {
		super(operations);
	}
	
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().println("Hello World");
	}	

}
