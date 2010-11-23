package com.cefn.filesystem;

import java.net.URL;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Locatable {
	
	@Id
	URL getLocation();
	
}
