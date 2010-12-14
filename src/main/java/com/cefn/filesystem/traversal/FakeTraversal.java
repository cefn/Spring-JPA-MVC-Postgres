package com.cefn.filesystem.traversal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.cefn.filesystem.Visitable;
import com.cefn.filesystem.Visitor;
import com.cefn.filesystem.factory.FileFactory;
import com.cefn.filesystem.factory.FolderFactory;

public class FakeTraversal implements Traversal {

	FolderFactory folderFactory;
	FileFactory fileFactory;
	
	@Inject
	public FakeTraversal(FolderFactory folderFactory, FileFactory fileFactory){
		this.folderFactory = folderFactory;
		this.fileFactory = fileFactory;		
	}
	
	@Override
	public Visitable<Folder> getFolderVisitable(final Filesystem fs) {
		try{
			//Place holder for Live implementation which returns Folders
			final List<Folder> folderList = Arrays.asList(new Folder[]{
					folderFactory.create(new URL(fs.getLocation(),"var/"), fs, null),
					folderFactory.create(new URL(fs.getLocation(),"opt/"), fs, null),
					folderFactory.create(new URL(fs.getLocation(),"home/"), fs, null)
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
					fileFactory.create(new URL(fs.getLocation(),"README.txt"), fs),
					fileFactory.create(new URL(fs.getLocation(),"index.html"), fs),
					fileFactory.create(new URL(fs.getLocation(),"play.mp3"), fs)
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
