package com.cefn.filesystem;

import javax.persistence.Entity;

@Entity
public interface Folder extends Locatable{
	
	Filesystem getFilesystem();
	Folder getParent();
	
}
