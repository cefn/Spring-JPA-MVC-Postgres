package com.cefn.filesystem.servlet;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import com.cefn.filesystem.App;
import com.cefn.filesystem.modules.ConfigModule;
import com.cefn.filesystem.modules.ConfigModule.Config;
import com.cefn.filesystem.test.RecordPlaybackTestApp;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceFilter;

/** Note, this scenario below will not work until guice-servlets is patched to handle bug 522...
 * http://code.google.com/p/google-guice/issues/detail?id=522
 * Using the App superclass will configure everything except the webserver, then you can use 
 * web.xml and jetty-maven-plugin to launch in eclipse, with mainstream jetty on the server.
 * This implementation is an alternative approach to the the basic webserver configuration 
 * you currently need to do in web.xml
 */
public class EmbeddedJettyApp extends App{
		
	public EmbeddedJettyApp(String... args){
		super(args);
	}
	
	public static void main(String[] args){
		new EmbeddedJettyApp(args).start();
	}
	
	/** Sadly I've been unable to remove dependency on web.xml because of guice-servlets bug 522 */
	private static boolean noXML = false;
	
	@Override
	protected List<Module> getModules(String[] args) {
		//load common modules from superclass
		List<Module> modules = super.getModules(args);
		//add your own modules
		modules.add( 
				noXML? 
				new GuiceRoutingModule() : 
				new XmlRoutingModule()
		);
		//return the augmented module list
		return modules;		
	};	
	
	class JettyLaunchModule extends AbstractModule{
		@Override
		protected void configure() {
			bind(Scenario.class).to(LaunchJettyServerScenario.class); //add a binding to bring in the ServerScenario
		}
	}

	class GuiceRoutingModule extends JettyLaunchModule{
		protected void configure() { 
			super.configure();
			bind(ServletContextInjectionAdaptor.class).to(GuiceRoutedServletContextInjectionAdaptor.class); //establish injector startup
		}
	}
	
	class XmlRoutingModule extends JettyLaunchModule{
		protected void configure() { 
			super.configure();
			bind(ServletContextInjectionAdaptor.class).to(XmlRoutedServletContextInjectionAdaptor.class); //establish injector startup
		}
	}
	
	private static class LaunchJettyServerScenario extends Scenario{
		@Inject ServletContextInjectionAdaptor servletContextAdaptor;
		@Inject @Config Properties configProperties;
				
		public void run(){
			try{
			   Server server = new Server(8080);
			   ServletContextHandler handler = null;
			   
			   if(noXML){ //TODO check if config sets location of web.xml or not, instead
				   //broken because of bug 522 - should be possible to wire in Guice without web.xml - 
				   handler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
			   }
			   else{
				   //loads from web.xml to enable forwarding filter to be added
				   WebAppContext xmlHandler = new WebAppContext();
				   xmlHandler.setContextPath("/");
				   xmlHandler.setDescriptor(configProperties.getProperty(ConfigModule.ROOT_PATH_PROPNAME) + "/WEB-INF/web.xml");
				   xmlHandler.setParentLoaderPriority(true);
				   handler = xmlHandler;
			   }			    

			   //wires in bespoke web routing through GUICE
			   //CH TODO removed to check if this is the source of the forwarding bug
			   handler.addFilter(GuiceFilter.class, "/*", 0);
			   handler.addEventListener(servletContextAdaptor);

			   //configures resource paths for loading files
			   handler.setResourceBase(configProperties.getProperty(ConfigModule.ROOT_PATH_PROPNAME));
			   
			   //makes sure of static serving and default error behaviour if all fails
			   handler.addServlet(DefaultServlet.class, "/");
			   
			   //launch jetty
			   server.setHandler(handler);
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
