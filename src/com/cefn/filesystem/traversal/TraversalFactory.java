package com.cefn.filesystem.traversal;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.cefn.filesystem.Visitable;

public interface TraversalFactory {

	Visitable<Folder> getFolderVisitable(Filesystem fs);
	
	Visitable<File> getFileVisitable(Folder fs);
	
}
