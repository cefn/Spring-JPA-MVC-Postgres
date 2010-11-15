package com.cefn.filesystem.factory;

import java.net.URL;

import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.sun.istack.internal.Nullable;

public interface FolderFactory {
	
	Folder create(URL url, Filesystem filesystem, @Nullable Folder parent);

}
