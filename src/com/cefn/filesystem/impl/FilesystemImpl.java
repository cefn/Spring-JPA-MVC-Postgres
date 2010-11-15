package com.cefn.filesystem.impl;

import java.net.URL;

import javax.inject.Inject;
import javax.persistence.Entity;

import com.cefn.filesystem.Filesystem;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

@Entity
public class FilesystemImpl extends LocatableImpl implements Filesystem{
	
	@AssistedInject
	FilesystemImpl(@Assisted URL url){
		super(url);
	}
	
}
