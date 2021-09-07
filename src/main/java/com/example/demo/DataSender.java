package com.example.demo;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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

        System.out.printf("server running");


        try(DatagramSocket serverSocket = new DatagramSocket(50000)) {
            while (true){
                int lat =random.nextInt(100);
                int lon =random.nextInt(100);
                int alt =random.nextInt(1000);
                int statusCode = random.nextInt(2);
                String status;

                if(statusCode == 0){
                    status = "Enemy";
                }
                else
                    status="Friendly";
                Message msg = new Message(lat,lon,alt,status);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(msg);
                byte[] msgArr = bStream.toByteArray();

                DatagramPacket packet = new DatagramPacket(
                        msgArr,
                        msgArr.length,
                        InetAddress.getLocalHost(),
                        clienPort
                );
                serverSocket.send(packet);

                Thread.sleep(2000);


            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

