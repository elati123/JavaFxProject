package com.example.demo;

public class SingletonDataSender {

    private static SingletonDataSender single_instance = null;


    public static int flag = 0;
    public int lat;
    public int lon;

    private SingletonDataSender(){
        this.lat = 0;
        this.lon = 0;

    }

    public static SingletonDataSender getInstance(){
        if (single_instance == null)
            single_instance = new SingletonDataSender();

        return single_instance;

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
