package com.cefn.filesystem;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface File extends Locatable{

	Folder getFolder();
		
}
