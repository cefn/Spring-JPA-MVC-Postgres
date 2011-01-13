package com.cefn.filesystem.modules;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;

public class ConfigModule extends AbstractModule{

	@BindingAnnotation @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD}) 
	public @interface Config {}
	
	public static final String DEFAULT_PROPERTIES_FILENAME = "config.properties";
	
	public static final String ROOT_PATH_PROPNAME = "com.cefn.filesystem.root_path";
	public static final String PROPERTIES_PATH_PROPNAME = "com.cefn.filesystem.properties_path";
	public static final String STATIC_PATH_PROPNAME = "com.cefn.filesystem.static_path";
	public static final String FREEMARKER_PATH_PROPNAME = "com.cefn.filesystem.freemarker_path";
		
	public static final String LOG_LEVEL_NAME = "com.cefn.filesystem.loglevel";

	public static final String LOG_ALL = "ALL"; //matches name of a define in java.util.logging.Level
		
	public Properties configureDefaults(Properties properties){
		lazySetProperty(properties, ROOT_PATH_PROPNAME, System.getProperty("user.dir")); //fall back to current working directory
		lazySetProperty(properties, STATIC_PATH_PROPNAME, properties.getProperty(ROOT_PATH_PROPNAME) + "/static");
		lazySetProperty(properties, FREEMARKER_PATH_PROPNAME, "file://" + properties.getProperty(ROOT_PATH_PROPNAME) + "/templates");
		lazySetProperty(properties, LOG_LEVEL_NAME, LOG_ALL); 
		return properties;
	}
	
	public void lazySetProperty(Properties properties, String name, String value){
		if(!properties.containsKey(name)){
			properties.setProperty(name, value);
		}
	}
	
	@Override
	protected void configure() {
		
		//Load default properties
		Properties configProperties = new Properties();
		
		//try to load overrided from default location, or VM-specified location of property file
		String configPathName = System.getProperty(PROPERTIES_PATH_PROPNAME); //explicit location set in VM?
		Level logLevel = Level.WARNING; //if filepath was explicit send warning on fail
		if(configPathName == null || configPathName.equals("")){ //filepath not explicit 
			configPathName = DEFAULT_PROPERTIES_FILENAME; //guess a path
			logLevel = Level.INFO; //no warning on fail
		}
		try{
			configProperties.load(new FileInputStream(configPathName));
		}
		catch(IOException ioe){
			Logger.getLogger(this.getClass().getName()).log(
					logLevel, "Could not load config properties from " + configPathName
			);
		}
		
		//configure defaults for any properties not set
		configureDefaults(configProperties);
		
		//make the resulting properties available to the injector
		bind(Properties.class).annotatedWith(Config.class).toInstance(configProperties);
		
	}
	
}
