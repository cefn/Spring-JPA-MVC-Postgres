package com.cefn.filesystem.servlet;

import java.util.Hashtable;
import java.util.Map;

import javax.inject.Singleton;

import org.eclipse.jetty.servlet.DefaultServlet;

import com.cefn.filesystem.servlet.BasicHttpServlet.BasicServletOperations;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import freemarker.ext.servlet.FreemarkerServlet;

/*
 * CH Alternatively and allegedly there is a pure java way to launch this, based on Jetty...
 * From http://code.google.com/p/google-guice/wiki/ServletModule
 *  
    Server server = new Server(portNumber);    
    Context root = new Context(server, "/", Context.SESSIONS);
    
    root.addFilter(GuiceFilter.class, "/*", 0);
    root.addServlet(DefaultServlet.class, "/");

    ...

    server.start();
 */

public class ServletContextAdaptor extends GuiceServletContextListener{

	@Override
	protected Injector getInjector() {
			return Guice.createInjector(new ServletModule(){
				@Override
				protected void configureServlets() {
					
					//used to handle operations demanded by servlet
					bind(BasicHttpServlet.ServletOperations.class).to(BasicServletOperations.class);

					//handle simple file serving from assets directory
					bind(DefaultServlet.class).in(Singleton.class);
					serve("/assets/*").with(DefaultServlet.class);
					
					//handle welcome journey
					bind(IndexServlet.class).in(Singleton.class);
					serve("/").with(IndexServlet.class);
										
					//serve freemarker template files
					bind(FreemarkerServlet.class).in(Singleton.class);
					Map<String,String> configMap = new Hashtable<String,String>();
					configMap.put("TemplatePath", "file:///home/cefn/Documents/bt/debatescape/cefn_guice_jpa/github/Spring-JPA-MVC-Postgres/src/main/webapp");
					configMap.put("NoCache", "true");					
					serve("*.ftl").with(FreemarkerServlet.class, configMap);

					//test page
					bind(HelloWorldServlet.class).in(Singleton.class);
					serve("/hello").with(HelloWorldServlet.class);

					
				}
			});
	}
	
}
