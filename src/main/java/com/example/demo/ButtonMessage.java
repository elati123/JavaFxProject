package com.example.demo;

import java.io.Serializable;

public class ButtonMessage implements Serializable {

    public int lat;
    public int lon;

    public ButtonMessage(int lat, int lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }
}
