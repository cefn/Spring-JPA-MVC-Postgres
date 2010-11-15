package com.cefn.filesystem.impl;

import java.net.URL;

import javax.annotation.Nullable;
import javax.persistence.Entity;

import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity
public class FolderImpl extends LocatableImpl implements Folder{
	
	private final Filesystem filesystem;
	private final Folder parent;
		
	@AssistedInject
	FolderImpl(@Assisted URL url, @Assisted Filesystem filesystem, @Nullable @Assisted Folder parent){
		super(url);
		this.parent = parent;
		this.filesystem = filesystem;
	}

	public Filesystem getFilesystem() {
		return filesystem;
	}
	
	public Folder getParent(){
		return parent;
	}
	

}
