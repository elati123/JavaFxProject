package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

public class DemoReceiver implements  Runnable{
    private final int port;

    public DemoReceiver(int port) {
        this.port = port;
    }




    @Override
    public void run() {
        System.out.printf("client working");
        try(DatagramSocket clientSocket = new DatagramSocket(port)){
            byte[] buffer = new byte[65507];
            while(true){
                DatagramPacket datagramPacket = new DatagramPacket(buffer,0,buffer.length);
                clientSocket.receive(datagramPacket);
                ByteArrayInputStream baos = new ByteArrayInputStream(datagramPacket.getData());
                ObjectInputStream oos = new ObjectInputStream(baos);

                Message receivedMessage = (Message)  oos.readObject();

                System.out.print(receivedMessage.status);
                int lon = receivedMessage.lon;
                int lat = receivedMessage.lat;
                int alt = receivedMessage.alt;
                String status = receivedMessage.status;


            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
