package com.cefn.filesystem;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.cefn.filesystem.impl.FilesystemImpl;
import com.cefn.filesystem.traversal.LiveTraversalFactory;
import com.cefn.filesystem.traversal.StoredTraversalFactory;

/** Accesses objects implementing interfaces with routines backed by a real filesystem. 
 * Stores the data accessed in this way through JPA annotations on POJO domain objects. 
 * Retrieves the file system data through objects backed by JPA Database retrieval.
*/
public class App {
	
	public static void main(String[] args){
		new App(args).run();
	}

	public App(String[] args){
		
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	public void run(){
		
		//Load spring dependency injection system
		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
		//tell it about the configuration object
		appContext.register(Config.class);
		//ask it to configure this app instance (in particular inject an EntityManager)
		this.entityManager = appContext.getBean(EntityManager.class);
		
		try{

			Filesystem liveFilesystem = new FilesystemImpl(new URL("file://c"));
			final LiveTraversalFactory liveFactory = new LiveTraversalFactory();
			
			//create and merge in-memory objects into the database
			liveFactory.getFolderVisitable(liveFilesystem).accept(new Visitor<Folder>() {
				@Override
				public void visit(Folder item) {
					liveFactory.getFileVisitable(item).accept(new Visitor<File>(){
						@Override
						public void visit(File item) {
							entityManager.merge(item);
						}
					});
				}
			});
			
			//retrieve and verify database-loaded objects
			Filesystem storedFilesystem = (Filesystem) entityManager.createQuery("SELECT fs FROM Filesystem fs").getSingleResult();
			final StoredTraversalFactory storedFactory = new StoredTraversalFactory(entityManager);
			
			storedFactory.getFolderVisitable(storedFilesystem).accept(new Visitor<Folder>() {
				@Override
				public void visit(Folder folder) {
					storedFactory.getFileVisitable(folder).accept(new Visitor<File>(){
						@Override
						public void visit(File file) {
							System.out.println("Retrieved file : " + file.getLocation());
						}
					});
				}
			});
		}
		catch(MalformedURLException mue){
			throw new RuntimeException(mue);
		}
		
	}
	
	@Configuration
	public static class Config {

		@Bean
		DataSource getDataSource(){
			SimpleDriverDataSource bean = new SimpleDriverDataSource(new org.postgresql.Driver(), "jdbc:postgresql://localhost/cefn", "cefn", "cefn");
			return bean;
		}
		
		@Bean 
		EntityManagerFactoryInfo getEntityManagerFactoryInfo(){
			LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();		
			return bean;
		}
				
	}
	
}
