package com.kodevian.reciclapp.model;

/**
 * Created by junior on 04/05/16.
 */
public class LocationTrack {
    private Location location;

    public LocationTrack(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
