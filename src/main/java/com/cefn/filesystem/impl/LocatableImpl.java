package com.cefn.filesystem.impl;

import java.net.URL;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Proxy;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Locatable;

@MappedSuperclass 
public class LocatableImpl implements Locatable{

	private URL location;

	@Id
	private String locationString;

	@Version
	int version;

	protected LocatableImpl(){
	}
	
	LocatableImpl(URL location){
		setLocation(location);
	}
		
	@Override
	public URL getLocation() {
		return location;
	}
	
	public void setLocation(URL location) {
		this.location = location;
		this.locationString = location.toExternalForm();
	}
}