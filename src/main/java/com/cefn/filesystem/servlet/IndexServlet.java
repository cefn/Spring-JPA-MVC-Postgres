package com.cefn.filesystem.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static final String URI_PATTERN = "^/([^/.]*)$";
	
	@Inject
	public IndexServlet(ServletOperations operations){
		super(operations);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Matcher matcher = Pattern.compile(URI_PATTERN).matcher(request.getRequestURI());
		matcher.find();
		String itemName = matcher.group(1);
		if(itemName == null || itemName.equals("")){
			itemName = "index";
		}
		operations.forward(request, response, "/" + itemName  +".ftl");
	}

}
