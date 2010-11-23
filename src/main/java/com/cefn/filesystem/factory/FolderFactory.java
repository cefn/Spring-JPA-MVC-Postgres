package com.cefn.filesystem.factory;

import java.net.URL;

import javax.annotation.Nullable;

import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;

public interface FolderFactory {
	
	Folder create(URL url, Filesystem filesystem, @Nullable Folder parent);

}
