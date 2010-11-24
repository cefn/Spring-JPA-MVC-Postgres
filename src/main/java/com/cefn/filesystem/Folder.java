package com.cefn.filesystem;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Folder extends Locatable{
	
	Filesystem getFilesystem();
	Folder getParent();
	
}
