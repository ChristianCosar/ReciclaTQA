package com.kodevian.reciclapp.model;

import java.io.Serializable;

/**
 * Created by manu on 10/12/15.
 */
public class LocationEntity implements Serializable {

    private double latitude;
    private double longitude;


    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationEntity(){

    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
