package com.cefn.filesystem.factory;

import java.net.URL;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Folder;

public interface FileFactory {
	public File create(URL url, Folder folder);
}
