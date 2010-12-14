package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

public class AboutServlet extends BasicHttpServlet{
	
	@Inject
	public AboutServlet(ServletOperations operations){
		super(operations);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		operations.forward(request, response, "/about.ftl");
	}

}
