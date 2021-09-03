package com.example.demo;

public class Track {
    // data typelar sonradan değişecek şimdilik int
    private int lon;  // longitude
    private int lat;   // latitdde
    private int alt;   // altitude
    private String status;

    public Track(int lon, int lat, int alt, String status) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
        this.status = status;
    }

    public int getLon() {
        return lon;
    }

    public int getLat() {
        return lat;
    }

    public int getAlt() {
        return alt;
    }

    public String getStatus() {
        return status;
    }
}