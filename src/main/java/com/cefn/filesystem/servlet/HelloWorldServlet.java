package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class HelloWorldServlet extends HttpServlet{
	
	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		response.getOutputStream().println("Hello world");
	}	

}
