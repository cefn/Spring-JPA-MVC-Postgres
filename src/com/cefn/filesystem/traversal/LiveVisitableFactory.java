package com.cefn.filesystem.traversal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.ws.BindingType;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.cefn.filesystem.Visitable;
import com.cefn.filesystem.Visitor;
import com.cefn.filesystem.impl.FileImpl;
import com.cefn.filesystem.impl.FolderImpl;

public class LiveVisitableFactory implements VisitableFactory{
	
	@Override
	public Visitable<Folder> getFolderVisitable(final Filesystem fs) {
		try{
			//Place holder for Live implementation which returns Folders
			final List<Folder> folderList = Arrays.asList(new Folder[]{
					new FolderImpl(new URL(fs.getLocation(),"/var"), fs),
					new FolderImpl(new URL(fs.getLocation(),"/opt"), fs),
					new FolderImpl(new URL(fs.getLocation(),"/home"), fs)
			});			
			return new Visitable<Folder>(){
				@Override
				public void accept(Visitor<Folder> visitor) {
					Iterator<Folder> folderIterator = folderList.iterator();
					while(folderIterator.hasNext()){
						visitor.visit(folderIterator.next());
					}
				}
			};			
		}
		catch(MalformedURLException mue){
			throw new RuntimeException(mue);
		}
	}

	@Override
	public Visitable<File> getFileVisitable(final Folder fs) {
		try{
			final List<File> fileList = Arrays.asList(new File[]{
					new FileImpl(new URL(fs.getLocation(),"README.txt"), fs),
					new FileImpl(new URL(fs.getLocation(),"index.html"), fs),
					new FileImpl(new URL(fs.getLocation(),"play.mp3"), fs)
			});			
			return new Visitable<File>(){
				@Override
				public void accept(Visitor<File> visitor) {
					Iterator<File> fileIterator = fileList.iterator();
					while(fileIterator.hasNext()){
						visitor.visit(fileIterator.next());
					}
				}
			};
		}
		catch(MalformedURLException mue){
			throw new RuntimeException(mue);
		}
	}
	
}
