package com.cefn.filesystem.traversal;

import com.cefn.filesystem.factory.FileFactory;
import com.cefn.filesystem.factory.FilesystemFactory;
import com.cefn.filesystem.factory.FolderFactory;

public abstract class AbstractTraversal implements Traversal{
	
	FolderFactory folderFactory;
	FileFactory fileFactory;
	
	public AbstractTraversal(FolderFactory folderFactory, FileFactory fileFactory){
		this.folderFactory = folderFactory;
		this.fileFactory = fileFactory;		
	}

}
