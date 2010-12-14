package com.cefn.filesystem.impl;

import java.io.Serializable;
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
@IdClass(LocatableImpl.LocationId.class)
public class LocatableImpl implements Locatable{

	private URL location;

	@Version
	int version;

	protected LocatableImpl(){
	}
	
	LocatableImpl(URL location){
		setLocation(location);
	}
		
	@Id
	public URL getLocation() {
		return location;
	}
	
	private void setLocation(URL location) {
		this.location = location;
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