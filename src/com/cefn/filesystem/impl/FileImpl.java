package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity(name="file")
public class FileImpl extends LocatableImpl implements File{

	@ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	private Folder folder;
		
	protected FileImpl(){		
	}
	
	@AssistedInject
	FileImpl(@Assisted URL url, @Assisted Folder folder){
		super(url);
		this.folder = folder;
	}	
	
	@Override
	public Folder getFolder() {
		return folder;
	}
		
}
