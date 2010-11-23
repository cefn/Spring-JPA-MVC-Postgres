package com.cefn.filesystem;

import javax.persistence.Entity;

@Entity
public interface File extends Locatable{

	Folder getFolder();
		
}
