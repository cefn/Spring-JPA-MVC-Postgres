package com.cefn.filesystem.servlet;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletContextHandler.Context;

import com.cefn.filesystem.App;
import com.cefn.filesystem.App.Scenario;
import com.cefn.filesystem.test.RecordPlaybackScenario;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;

public class ServerApp extends App{
	
	public static void main(String[] args){
		new ServerApp().configureScenario(args).run();		
	}

	@Override
	protected List<Module> getModules(String[] args) {
		
		//load common modules from superclass
		List<Module> modules = super.getModules(args);
		//add a binding to bring in the ServerScenario
		modules.add(new AbstractModule(){ 
			protected void configure() { bind(Scenario.class).to(ServerScenario.class); } 
		});
		//return the augmented module list
		return modules;
		
	};	
	
	public static class ServerScenario extends Scenario{
		
		public void run(){
			try{
			    Server server = new Server(8080);
			    ServletContextHandler root = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
			    
			    root.addFilter(GuiceFilter.class, "/*",EnumSet.allOf(DispatcherType.class));
			    root.addEventListener(new ServletContextAdaptor());
			    root.addServlet(DefaultServlet.class, "/");
			    
			    server.start();
			    server.join();
			}
			catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException(e);				
			}
		}
		
	}

}
