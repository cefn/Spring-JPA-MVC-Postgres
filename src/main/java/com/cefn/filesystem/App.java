package com.cefn.filesystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cefn.filesystem.factory.FilesystemFactory;
import com.cefn.filesystem.modules.ConfigModule;
import com.cefn.filesystem.modules.FilesystemModule;
import com.cefn.filesystem.modules.MainModule;
import com.cefn.filesystem.modules.FilesystemModule.Stored;
import com.cefn.filesystem.modules.FilesystemModule.Actual;
import com.cefn.filesystem.modules.MainModule.Args;
import com.cefn.filesystem.traversal.Traversal;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public abstract class App {
	
	private Injector injector;
		
	/** Should configure from command line arguments (if any) and load everything needed for injection and execution. 
	 * Execution of code is classically through getInjector().getInstance(Scenario.class).run() in the case of an embedded
	 * java usage. Where the main java process arises in a webserver, or this object is used as a utility class to configure
	 * everything, then after constructing, simply get the injector and use it. */
	public App(String... args){
		//load the injector		
		injector = createInjector(args);
		//always start up persistence first
		injector.getInstance(PersistService.class).start();			
	}

	public void start(){
		Scenario scenario = injector.getInstance(Scenario.class);
		if(scenario != null){
			Thread scenarioThread = new Thread(scenario);
			scenarioThread.start();
		}
		else{
			throw new RuntimeException("Fatal error: Calling App#start() requires a Scenario to be available to the Injector");
		}
	}
	
	protected Injector createInjector(String[] args){
		if(injector == null){
			injector = Guice.createInjector(getModules(args));			
			return injector;
		}
		else{
			throw new RuntimeException("Injector already exists; can't be re-initialised");
		}
	}
	
	public Injector getInjector(){
		return injector;
	}
	
	protected List<Module> getModules(String[] args){
		return new ArrayList<Module>(Arrays.asList(
			new Module[]{
				new MainModule(args), //capability to handle command line arguments
				new ConfigModule(), //capability to load properties from a file
				new JpaPersistModule("hibernate"), //hibernate persistence 
				new FilesystemModule() //business objects
			}
		));
	}
		
	public static abstract class Scenario implements Runnable{
		@Inject @Args protected String[] args; //arguments loaded from command line
		@Inject protected PersistService persistService; //persistence layer
		@Inject protected EntityManager entityManager; //entity manager backed by persistence layer
		
		@Inject protected FilesystemFactory filesystemFactory; //factory for objects implementing Business Object interfaces
		@Inject @Actual protected Traversal liveTraversal; //Traversal of live filesystem 
		@Inject @Stored protected Traversal storedTraversal; //Traversal of stored filesystem
	}

}
