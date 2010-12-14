package com.cefn.filesystem.traversal;

import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.cefn.filesystem.Visitable;
import com.cefn.filesystem.Visitor;
import com.cefn.filesystem.factory.FileFactory;
import com.cefn.filesystem.factory.FolderFactory;


public class LiveTraversal implements Traversal{
	
	FolderFactory folderFactory;
	FileFactory fileFactory;
	
	@Inject
	public LiveTraversal(FolderFactory folderFactory, FileFactory fileFactory){
		this.folderFactory = folderFactory;
		this.fileFactory = fileFactory;		
	}
	
	List<java.io.File> listFoldersRecursively(URI ancestorUri){
		java.io.File rootFile = new java.io.File(ancestorUri);
		List<java.io.File> folders = new ArrayList<java.io.File>();
		appendFoldersRecursively(rootFile, folders);
		return folders;
	}
	
	private void appendFoldersRecursively(java.io.File ancestor, final List<java.io.File> folderList){
		java.io.File[] folders = ancestor.listFiles(new FileFilter() {
			public boolean accept(java.io.File file) {
				if(file.isDirectory()){
					folderList.add(file);
					appendFoldersRecursively(file, folderList);
					return true;
				}
				return false;
			}
		});
	}
	
	
	
	
	@Override
	public Visitable<Folder> getFolderVisitable(final Filesystem fs) {
		return new Visitable<Folder>(){
			@Override
			public void accept(Visitor<Folder> visitor) {
				try{
					List<java.io.File> folders = listFoldersRecursively(fs.getLocation().toURI());
					if(folders != null){
						for(java.io.File folder:folders){
							visitor.visit(folderFactory.create(folder.toURL(), fs, null));
						}						
					}
				}
				catch(IOException ioe){
					Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ioe.toString());
				}
				catch(URISyntaxException use){
					Logger.getLogger(this.getClass().getName()).log(Level.WARNING, use.toString());
				}
			}
		};
	}

	@Override
	public Visitable<File> getFileVisitable(final Folder fo) {
		return new Visitable<File>(){
			@Override
			public void accept(Visitor<File> visitor) {
				try{
					java.io.File[] files = new java.io.File(fo.getLocation().toURI()).listFiles(new FileFilter() {
						public boolean accept(java.io.File file) {
							return file.isFile();
						}
					});
					if(files != null){
						for(java.io.File file:files){
							visitor.visit(fileFactory.create(file.toURL(), fo));
						}						
					}
				}
				catch(IOException ioe){
					Logger.getLogger(this.getClass().getName()).log(Level.WARNING, ioe.toString());
				}
				catch(URISyntaxException use){
					Logger.getLogger(this.getClass().getName()).log(Level.WARNING, use.toString());
				}
			}
		};
	}

}
