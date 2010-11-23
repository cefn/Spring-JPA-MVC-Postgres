package com.cefn.filesystem;

public interface Visitable<T> {

	public void accept(Visitor<T> visitor);
	
}
