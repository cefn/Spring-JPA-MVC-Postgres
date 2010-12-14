package com.cefn.filesystem.test;

import java.util.List;

import com.cefn.filesystem.App;
import com.google.inject.AbstractModule;
import com.google.inject.Module;

/** Accesses objects implementing interfaces with routines backed by a real file system. 
 * Stores the data accessed in this way through JPA annotations on POJO domain objects. 
 * Retrieves the file system data through objects backed by JPA Database retrieval.
*/
public class RecordPlaybackTestApp extends App{
	
	public static void main(String[] args){
		new RecordPlaybackTestApp().configureScenario(args).run();
	}
	
	@Override
	protected List<Module> getModules(String[] args) {
		
		//load common modules from superclass
		List<Module> modules = super.getModules(args);

		//add a binding to bring in the SaveLoadScenario
		modules.add(new AbstractModule(){ 
			protected void configure() { bind(Scenario.class).to(RecordPlaybackScenario.class); } 
		});
		
		//return the augmented module list
		return modules;
		
	};
				
}
