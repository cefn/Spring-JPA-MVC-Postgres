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

	protected final Injector createInjector(String[] args){
		return Guice.createInjector(getModules(args));
	}

	public final Scenario configureScenario(String[] args){
		//load the injector
		Injector injector = createInjector(args);
		//start up persistence
		injector.getInstance(PersistService.class).start();			
		//return the injected runnable
		return injector.getInstance(Scenario.class);
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
