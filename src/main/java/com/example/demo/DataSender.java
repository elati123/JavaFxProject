package com.example.demo;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;

public class DataSender implements Runnable {
    private final int clienPort;

    public ArrayList<Message> message = new ArrayList<Message>();

    public DataSender(int clienPort) {
        this.clienPort = clienPort;
    }


    @Override
    public void run() {
        Random random = new Random();




        try(DatagramSocket serverSocket = new DatagramSocket(50000)) {
            while (true){
                int lat =random.nextInt(100);
                int lon =random.nextInt(100);
                int alt =random.nextInt(1000);
                int statusCode = random.nextInt(1);
                String status;

                if(statusCode == 0){
                    status = "Enemy";
                }
                else
                    status="Friendly";
                Message msg = new Message(lat,lon,alt,status);
                message.add(msg)
                 DatagramPacket packet = new DatagramPacket(
                         message.getBytes();

            );

            }

        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
