package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Folder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity(name="file")
@Table(name="file")
public class FileImpl extends LocatableImpl implements File{

	private Folder folder;
		
	protected FileImpl(){
	}
	
	@AssistedInject
	FileImpl(@Assisted URL url, @Assisted Folder folder){
		super(url);
		this.folder = folder;
	}	
	
	@ManyToOne(targetEntity = FolderImpl.class,cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	public Folder getFolder() {
		return folder;
	}
	
	private void setFolder(Folder folder) {
		this.folder = folder;
	}
		
}