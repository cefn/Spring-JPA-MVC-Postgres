package com.cefn.filesystem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.cefn.filesystem.App.FilesystemModule.Fake;
import com.cefn.filesystem.App.FilesystemModule.Real;
import com.cefn.filesystem.App.MainModule.Args;
import com.cefn.filesystem.factory.FileFactory;
import com.cefn.filesystem.factory.FilesystemFactory;
import com.cefn.filesystem.factory.FolderFactory;
import com.cefn.filesystem.impl.FileImpl;
import com.cefn.filesystem.impl.FilesystemImpl;
import com.cefn.filesystem.impl.FolderImpl;
import com.cefn.filesystem.traversal.CachedTraversal;
import com.cefn.filesystem.traversal.LiveTraversal;
import com.cefn.filesystem.traversal.Traversal;
import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public abstract class App {
	
	protected List<Module> getModules(String[] args){		
		return new ArrayList<Module>(Arrays.asList(
			new Module[]{
				new MainModule(args),
				new FilesystemModule(),
				new JpaPersistModule("hibernate")			
			}
		));
	}
	
	protected Injector createInjector(String[] args){
		return Guice.createInjector(getModules(args));
	}

	public Scenario configureScenario(String[] args){
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
	
	public static class MainModule extends AbstractModule{		
		
		@BindingAnnotation @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD}) 
		public @interface Args {}	
		
		private final String[] args;
		
		public MainModule(String[] args){
			this.args = args;
		}
		
		@Override
		protected void configure() {

			//make arguments globally available to constructors carrying Args annotation
			bind(String[].class).annotatedWith(Args.class).toInstance(args);
			
		}
		
	}
	
		
	public static class FilesystemModule extends AbstractModule{
		
		@BindingAnnotation @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD}) 
		public @interface Real {}

		@BindingAnnotation @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD}) 
		public @interface Fake {}
		
		protected void configure() {						
		
			FactoryModuleBuilder factoryModuleBuilder = new FactoryModuleBuilder();
			install(factoryModuleBuilder.implement(Filesystem.class, FilesystemImpl.class).build(FilesystemFactory.class));
			install(factoryModuleBuilder.implement(Folder.class, FolderImpl.class).build(FolderFactory.class));
			install(factoryModuleBuilder.implement(File.class, FileImpl.class).build(FileFactory.class));
			
			/** Constructs filesystem object on the fly by traversing file system. */
			bind(Traversal.class).annotatedWith(Real.class).to(LiveTraversal.class);
			
			/** Constructs filesystem objects on the fly by loading from database. */
			bind(Traversal.class).annotatedWith(Fake.class).to(CachedTraversal.class);
			
		}
											
	}

	

}
