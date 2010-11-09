package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Folder;

@Entity
public class FileImpl extends LocatableImpl implements File{

	private final Folder page;
	
	public FileImpl(URL url, Folder page){
		super(url);
		this.page = page;
	}	
	
	@Override
	@ManyToOne
	public Folder getFolder() {
		return page;
	}
	
}
