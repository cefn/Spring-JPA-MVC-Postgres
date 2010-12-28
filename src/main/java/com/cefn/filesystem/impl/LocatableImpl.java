package com.cefn.filesystem.impl;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

import com.cefn.filesystem.Locatable;

@Entity(name="locatable") 
@Inheritance(strategy=InheritanceType.JOINED)
public class LocatableImpl implements Locatable{

	private String locationString;
	
	@Version
	int version;

	protected LocatableImpl(){
	}
	
	LocatableImpl(URL location){
		setLocation(location);
	}

	@Id
	public String getLocationString() {
		return locationString;
	}
	
	public void setLocationString(String locationString) {
		this.locationString = locationString;
	}
	
	public URL getLocation() {
		try{
			return new URL(locationString);			
		}
		catch(MalformedURLException mue){
			//should never happen
			throw new RuntimeException(mue);
		}
	}
	
	private void setLocation(URL location) {
		this.locationString = location.toExternalForm();	
	}
	
	public static class LocationId implements Serializable{

        public URL location;
        
        public URL getLocation() {
			return location;
		}
        
        public void setLocation(URL location) {
			this.location = location;
		}

        /**
         * Equality must be implemented in terms of identity field
         * equality, and must use instanceof rather than comparing 
         * classes directly (some JPA implementations may subclass the
         * identity class).
         */
        public boolean equals(Object other) {
            if (other == this)
                return true;
            if (!(other instanceof LocationId))
                return false;    
            LocationId otherid = (LocationId) other;
            return  location.equals(otherid.location);
        }
     
        /**
         * Hashcode must also depend on identity values.
         */
        public int hashCode() {
            return location == null ? 0 : location.hashCode();
        } 

        public String toString() {
            return "Url:" + location.toString();
        }
        
    }
	
}