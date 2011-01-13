package com.cefn.filesystem.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.google.inject.Injector;

public class BasicHttpServlet extends HttpServlet{
	
	protected ServletOperations operations;
		
	private static String SERVLET_CONTEXT_INJECTOR_ATTRIBUTENAME = Injector.class.getName();
	
	public static Injector getInjector(ServletContext sc){
		return (Injector)sc.getAttribute(SERVLET_CONTEXT_INJECTOR_ATTRIBUTENAME);
	}
	
	static void setInjector(ServletContext sc, Injector injector){
		synchronized(sc){
			if(getInjector(sc) == null){
				sc.setAttribute(SERVLET_CONTEXT_INJECTOR_ATTRIBUTENAME, injector);
			}			
			else{
				throw new RuntimeException("Error : Cannot set BasicHttpServlet injector singleton more than once. Check your config.");
			}
		}
	}
	
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
