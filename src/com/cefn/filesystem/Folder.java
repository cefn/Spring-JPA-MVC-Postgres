package com.cefn.filesystem;

public interface Folder extends Locatable{
	
	Filesystem getFilesystem();
	Folder getParent();
	
}
