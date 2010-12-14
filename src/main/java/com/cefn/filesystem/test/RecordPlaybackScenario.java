/**
 * 
 */
package com.cefn.filesystem.test;

import java.net.MalformedURLException;
import java.net.URL;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.App.Scenario;
import com.cefn.filesystem.traversal.DepthFirstFileVisitor;

public class RecordPlaybackScenario extends Scenario{
	
	@Override
	public void run() {

		//ask it to configure this app instance (in particular inject an EntityManager)
		try{			

			Filesystem filesystemInput = filesystemFactory.create(new URL("file:///home/cefn/"));
			
			/* Traverse live file hierarchy depth first, storing data */
			new DepthFirstFileVisitor(liveTraversal) {
				public void visit(File f) {
					entityManager.getTransaction().begin();
					f = entityManager.merge(f);
					entityManager.getTransaction().commit();
				}
			}.visit(filesystemInput);
			
			
			/** Retrieve file system object from database */
			Filesystem filesystemOutput = (Filesystem)entityManager.createQuery("SELECT fs FROM filesystem AS fs").getSingleResult();
			
			/* Traverse stored file hierarchy depth first, printing out data */
			new DepthFirstFileVisitor(storedTraversal) {
				public void visit(File f) {
					System.out.println("Retrieved file : " + f.getLocation());
				}
			}.visit(filesystemOutput);
			
		}
		catch(MalformedURLException mue){
			throw new RuntimeException(mue);
		}
		
	}
}