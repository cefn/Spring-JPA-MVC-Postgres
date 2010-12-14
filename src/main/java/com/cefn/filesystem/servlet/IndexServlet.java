package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cefn.filesystem.servlet.BasicHttpServlet.ServletOperations;
import com.google.inject.Inject;

public class IndexServlet extends BasicHttpServlet{
	
	@Inject
	public IndexServlet(ServletOperations operations){
		super(operations);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		operations.forward(request, response, "/templates/index.ftl");
	}

}
