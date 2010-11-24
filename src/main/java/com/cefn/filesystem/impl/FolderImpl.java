package com.cefn.filesystem.impl;

import java.net.URL;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity(name="folder") 
@Table(name="folder")
public class FolderImpl extends LocatableImpl implements Folder{
	
	private Filesystem filesystem;
	
	@Nullable
	private Folder parent;
	
	protected FolderImpl(){
		
	}
	
	@AssistedInject
	FolderImpl(@Assisted URL url, @Assisted Filesystem filesystem, @Nullable @Assisted Folder parent){
		super(url);
		this.parent = parent;
		this.filesystem = filesystem;
	}

	@ManyToOne(targetEntity = FilesystemImpl.class, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	public Filesystem getFilesystem() {
		return filesystem;
	}
	
	private void setFilesystem(Filesystem filesystem) {
		this.filesystem = filesystem;
	}
		
	@ManyToOne(targetEntity = FolderImpl.class,cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	public Folder getParent(){
		return parent;
	}
	
	private void setParent(Folder parent) {
		this.parent = parent;
	}
	
}
