package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.Entity;

import com.cefn.filesystem.Filesystem;

@Entity
public class FilesystemImpl extends LocatableImpl implements Filesystem{
	
	public FilesystemImpl(URL url){
		super(url);
	}
	
}
