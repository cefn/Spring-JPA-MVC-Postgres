package com.cefn.filesystem.impl;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.cefn.filesystem.Pin;
import com.cefn.filesystem.Locatable;

@Entity(name="annotation")
@Table(name="annotation")
public class PinImpl implements Pin{
	
	private Locatable locatable;
	
	public PinImpl(Locatable locatable){
		this.locatable = locatable;
	}
	
	@Override
	public Locatable getLocatable() {
		return locatable;
	}

}
