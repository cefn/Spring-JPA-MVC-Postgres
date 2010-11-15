package com.cefn.filesystem.impl;

import java.net.URL;

import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity
public class FileImpl extends LocatableImpl implements File{

	private final Folder page;
		
	@AssistedInject
	FileImpl(@Assisted URL url, @Assisted Folder page){
		super(url);
		this.page = page;
	}	
	
	@Override
	@ManyToOne
	public Folder getFolder() {
		return page;
	}
	
}
