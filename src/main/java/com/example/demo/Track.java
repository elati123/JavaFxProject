package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Track  {

    // data typelar sonradan değişecek şimdilik int
    private int lon;  // longitude
    private int lat;   // latitdde
    private int alt;   // altitude
    private String status;
    public Button button;


    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public Track(int lon, int lat, int alt, String status) {

        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
        this.status = status;
        this.button = new Button("Details");
        EventHandler handler = new EventHandler<ActionEvent> () {
            @Override
            public void handle(ActionEvent event) {



                try(DatagramSocket serverSocket = new DatagramSocket(50005)) {
                    System.out.println("BUTTON BASILDI");
                    ButtonMessage msg = new ButtonMessage(getLat(),getLon());
                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    ObjectOutput oo = new ObjectOutputStream(bStream);
                    oo.writeObject(msg);
                    byte[] msgArr = bStream.toByteArray();

                    DatagramPacket packet = new DatagramPacket(
                            msgArr,
                            msgArr.length,
                            InetAddress.getLocalHost(),
                            50002

                    );
                    serverSocket.send(packet);

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };
      button.setOnAction(handler);


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