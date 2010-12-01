package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.cefn.filesystem.Filesystem;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity(name="filesystem")
@Table(name="filesystem")
public class FilesystemImpl extends LocatableImpl implements Filesystem{
	
	protected FilesystemImpl(){}
	
	@AssistedInject
	FilesystemImpl(@Assisted URL url){
		super(url);
	}
	
}
