package com.cefn.filesystem.servlet;

import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.cefn.filesystem.App;
import com.cefn.filesystem.modules.ConfigModule.Config;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class XmlRoutedServletContextInjectionAdaptor extends ServletContextInjectionAdaptor{
	
	protected Injector getInjector() {
		synchronized(notifyingServletContextLock){
			if(notifyingServletContext != null){
				
				return new App(){}.getInjector();
			}
			else{
				throw new RuntimeException("Error: ServletContextAdaptor#getInjector() should only be called during ServletContext initialisation. Use BasicHttpServlet#getInjector()");
			}
		}
	}


}
