package com.cefn.filesystem;

public interface Visitor<T> {
	public void visit(T item);
}
