package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.cefn.filesystem.Locatable;

@MappedSuperclass
public class LocatableImpl implements Locatable{

	@Id
	private URL location;
	
	int version;
		
	LocatableImpl(URL location){
		this.location = location;
	}
	
	@Override
	public URL getLocation() {
		return location;
	}
}