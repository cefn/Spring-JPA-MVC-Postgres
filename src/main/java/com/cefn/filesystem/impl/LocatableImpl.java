package com.cefn.filesystem.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.cefn.filesystem.Locatable;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class LocatableImpl implements Locatable{

	private URL location;

	private String locationString;

	@Version
	int version;

	protected LocatableImpl(){
	}
	
	LocatableImpl(URL location){
		setLocation(location);
	}

	@Id
	public String getLocationString() {
		return locationString;
	}
	
	private void setLocationString(String locationString) throws MalformedURLException {
		this.locationString = locationString;
		this.location = new URL(locationString);
	}
	
	public URL getLocation() {
		return location;
	}
	
	private void setLocation(URL location) {
		this.location = location;
		this.locationString = location.toExternalForm();
	}
	
}