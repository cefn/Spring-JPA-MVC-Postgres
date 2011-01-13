package com.cefn.filesystem.servlet;

import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.cefn.filesystem.modules.ConfigModule.Config;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public abstract class ServletContextInjectionAdaptor extends GuiceServletContextListener{
	
	@Inject @Config Properties configProperties;
	
	protected ServletContext notifyingServletContext = null;
	protected int[] notifyingServletContextLock = new int[0];
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		synchronized(notifyingServletContextLock){
			notifyingServletContext = servletContextEvent.getServletContext();
			super.contextInitialized(servletContextEvent);			
			notifyingServletContext = null;
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		synchronized(notifyingServletContextLock){
			notifyingServletContext = servletContextEvent.getServletContext();
			super.contextDestroyed(servletContextEvent);
			notifyingServletContext = null;
		}
	}

	protected void setNotifyingServletContextInjector(Injector injector){
		synchronized(notifyingServletContextLock){
			BasicHttpServlet.setInjector(notifyingServletContext, injector);	
		}
	}

}
