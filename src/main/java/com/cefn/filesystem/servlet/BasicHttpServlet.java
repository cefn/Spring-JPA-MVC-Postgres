package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class BasicHttpServlet extends HttpServlet{
	
	protected ServletOperations operations;
	
	public BasicHttpServlet(ServletOperations operations){
		this.operations = operations;
	}
	
	public interface ServletOperations{
		public void forward(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException;
	}
	
	public static class BasicServletOperations implements ServletOperations{
		
		@Override
		public void forward(HttpServletRequest request, HttpServletResponse response, String path) throws IOException, ServletException{			
			RequestDispatcher dispatcher = request.getRequestDispatcher(path);
			dispatcher.forward(request, response);
		}
		
	}
	
}
