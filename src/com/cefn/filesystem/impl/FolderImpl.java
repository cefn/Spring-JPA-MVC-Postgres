package com.cefn.filesystem.impl;

import java.net.URL;

import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;

public class FolderImpl extends LocatableImpl implements Folder{
	
	private final Filesystem filesystem;
	private final Folder parent;
	
	public FolderImpl(URL url, Filesystem filesystem){
		super(url);
		this.parent = null;
		this.filesystem = filesystem;
	}

	public FolderImpl(URL url, Folder parent){
		super(url);
		this.parent = parent;
		this.filesystem = parent.getFilesystem();
	}

	public Filesystem getFilesystem() {
		return filesystem;
	}
	
	public Folder getParent(){
		return parent;
	}
	

}
