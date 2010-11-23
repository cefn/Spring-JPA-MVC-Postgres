package com.cefn.filesystem.traversal;

import com.cefn.filesystem.File;
import com.cefn.filesystem.Filesystem;
import com.cefn.filesystem.Folder;
import com.cefn.filesystem.Visitor;

public abstract class DepthFirstFileVisitor{

	Traversal visitableFactory;
	
	public DepthFirstFileVisitor(Traversal visitableFactory) {
		this.visitableFactory = visitableFactory;
	}
		
	public void visit(Filesystem f){
		visitableFactory.getFolderVisitable(f).accept(new Visitor<Folder>() {
			@Override
			public void visit(Folder item) {
				DepthFirstFileVisitor.this.visit(item);
			}
		});
	}

	public void visit(Folder f){
		visitableFactory.getFileVisitable(f).accept(new Visitor<File>() {
			@Override
			public void visit(File item) {
				DepthFirstFileVisitor.this.visit(item);
			}
		});						
	}
	
	public abstract void visit(File f);	
	
}
