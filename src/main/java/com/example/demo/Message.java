package com.example.demo;

import java.io.Serializable;

public class Message implements Serializable {

    int lat;
    int lon;
    int alt;
    String status;

    public Message(int lat, int lon, int alt, String status) {
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.status = status;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getAlt() {
        return alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
