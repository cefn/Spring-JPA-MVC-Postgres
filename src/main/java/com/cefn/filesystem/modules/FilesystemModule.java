/**
 * 
 */
package com.cefn.filesystem.modules;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
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
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class FilesystemModule extends AbstractModule{
	
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