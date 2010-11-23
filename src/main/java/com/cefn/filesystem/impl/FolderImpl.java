package com.cefn.filesystem.impl;

import java.net.URL;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity(name="folder") @Proxy(proxyClass=Folder.class)
public class FolderImpl extends LocatableImpl implements Folder{
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Filesystem filesystem;
	
	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
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

	public Filesystem getFilesystem() {
		return filesystem;
	}
	
	public Folder getParent(){
		return parent;
	}
	
}
