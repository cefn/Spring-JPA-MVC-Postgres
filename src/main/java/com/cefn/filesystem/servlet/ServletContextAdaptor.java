package com.cefn.filesystem.servlet;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.servlet.DefaultServlet;

import com.cefn.filesystem.modules.ConfigModule;
import com.cefn.filesystem.modules.ConfigModule.Config;
import com.cefn.filesystem.servlet.BasicHttpServlet.BasicServletOperations;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import freemarker.ext.servlet.FreemarkerServlet;

public class ServletContextAdaptor extends GuiceServletContextListener{

	@Inject @Config Properties configProperties;

	@Override
	protected Injector getInjector() {
			return Guice.createInjector(new ServletModule(){
				@Override
				protected void configureServlets() {
					//TODO CH check for hard-coded values below and export them to ConfigModule

					//pluggable operations handler
					bind(BasicHttpServlet.ServletOperations.class).to(BasicServletOperations.class);
					
					//sanity check page
					bind(HelloWorldServlet.class).in(Singleton.class);
					serve("/hello.world").with(HelloWorldServlet.class);
					
					//add stripes filter 
					bind(StripesFilter.class).in(Singleton.class);
					Map<String,String> stripesFilterParams = new HashMap<String,String>();
					stripesFilterParams.put("ActionResolver.Packages", "net.sourceforge.stripes.examples");
					filter("*").through(StripesFilter.class, stripesFilterParams);

					//add binding for Stripes actions
					bind(DispatcherServlet.class).in(Singleton.class);
					serve("*.action").with(DispatcherServlet.class);					

					//serve jsp template files
					bind(JspServlet.class).in(Singleton.class);
					serve("*.jsp").with(JspServlet.class);
					
					//handle welcome journey
					bind(IndexServlet.class).in(Singleton.class);
					serveRegex(IndexServlet.URI_PATTERN).with(IndexServlet.class);
										
					//handle simple file serving from static assets directory
					bind(DefaultServlet.class).in(Singleton.class);
					Map<String,String> defaultServletParams = new HashMap<String, String>();
					defaultServletParams.put("resourceBase", configProperties.getProperty(ConfigModule.STATIC_PATH_PROPNAME));
					defaultServletParams.put("maxCacheSize", "0"); 					
					serve("*.css").with(DefaultServlet.class, defaultServletParams);
										
					//serve freemarker template files
					bind(FreemarkerServlet.class).in(Singleton.class);
					Map<String,String> freemarkerServletParams = new HashMap<String, String>();
					freemarkerServletParams.put("TemplatePath", configProperties.getProperty(ConfigModule.FREEMARKER_PATH_PROPNAME));
					freemarkerServletParams.put("NoCache", "true");
					freemarkerServletParams.put("template_update_delay", "0");
					serve("*.ftl").with(FreemarkerServlet.class, freemarkerServletParams);					
					
				}
			});
	}
	
}
