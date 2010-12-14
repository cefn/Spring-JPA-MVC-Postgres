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
import com.cefn.filesystem.modules.FilesystemModule.Fake;
import com.cefn.filesystem.modules.FilesystemModule.Real;
import com.cefn.filesystem.modules.MainModule.Args;
import com.cefn.filesystem.traversal.Traversal;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public abstract class App {
	
	protected List<Module> getModules(String[] args){		
		return new ArrayList<Module>(Arrays.asList(
			new Module[]{
				new MainModule(args),
				new ConfigModule(),
				new FilesystemModule(),
				new JpaPersistModule("hibernate")			
			}
		));
	}
	
	protected final Injector createInjector(String[] args){
		return Guice.createInjector(getModules(args));
	}

	public final Scenario configureScenario(String[] args){
		Injector injector = createInjector(args);
		
		//initialise persistence
		injector.getInstance(PersistService.class).start();			

		//load the runnable
		return injector.getInstance(Scenario.class);
	}
	
	public static abstract class Scenario implements Runnable{

		@Inject @Args protected String[] args;
		
		@Inject protected PersistService persistService;
		@Inject protected EntityManager entityManager;
		
		@Inject protected FilesystemFactory filesystemFactory;

		@Inject @Real protected Traversal liveTraversal;
		@Inject @Fake protected Traversal storedTraversal;
						
	}

	

}
