package com.cefn.filesystem.factory;

import java.net.URL;

import com.cefn.filesystem.Filesystem;

public interface FilesystemFactory {

	public Filesystem create(URL url);
	
}
