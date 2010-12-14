/**
 * 
 */
package com.cefn.filesystem.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.AbstractModule;
import com.google.inject.BindingAnnotation;

public class MainModule extends AbstractModule{		
	
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