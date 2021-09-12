package com.example.demo;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;

import javafx.scene.web.WebView;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Button showAllButton;

    @FXML
    public AnchorPane pane;

    @FXML
    public TableView<Track> table;

    @FXML
    public TableColumn<Track, Integer> lat;

    @FXML
    public TableColumn<Track,Integer> lon;

    @FXML
    public TableColumn<Track, Integer> alt;

    @FXML
    public TableColumn<Track, String> status;

    @FXML
    public TableColumn<Track, Button> button;

    @FXML
    public Button exit;

    @FXML
    public WebView webview;

    @FXML
    public WebEngine engine;

    ObservableList<Track> list = FXCollections.observableArrayList(
            new Track(21,1,12,"e")

    );

    public Controller(){
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webview.getEngine();
        loadPage();
        lat.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lat"));
        lon.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lon"));
        alt.setCellValueFactory(new PropertyValueFactory<Track,Integer>("alt"));
        status.setCellValueFactory(new PropertyValueFactory<Track,String>("status"));
        button.setCellValueFactory(new PropertyValueFactory<Track,Button>("button"));


        table.setItems(list);
        System.out.println(table.getItems());
        System.out.println(
                "çalışıyor"
        );
        int port = 50001;
        DataSender server = new DataSender(port);
        Thread serverT = new Thread(server);

        //couldnt use a seperate client class since i cant make singleton controller objets
        listen(port);

        serverT.start();

        receiveButtonData();




    }
    //Deno function for table functionality



    public void close(ActionEvent event){

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
        // Terminate all threads
        System.exit(0);

    }
    // Function that adds data it listens
    public void listen(int port){

        Thread listen = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("client working");
                try(DatagramSocket clientSocket = new DatagramSocket(port)){
                    byte[] buffer = new byte[65507];
                    while(true){
                        DatagramPacket datagramPacket = new DatagramPacket(buffer,0,buffer.length);
                        clientSocket.receive(datagramPacket);
                        ByteArrayInputStream baos = new ByteArrayInputStream(datagramPacket.getData());
                        ObjectInputStream oos = new ObjectInputStream(baos);

                        Message receivedMessage = (Message)  oos.readObject();

                        int lon = receivedMessage.lon;
                        int lat = receivedMessage.lat;
                        int alt = receivedMessage.alt;
                        String status = receivedMessage.status;
                        table.getItems().add(new Track(lon,lat,alt,status));

                    }


                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                
            }
        });
        listen.start();


    }



    public void loadPage(){
        engine.load(getClass().getResource("map/map.html").toExternalForm());

    }


    public void receiveButtonData(){

        Thread listen = new Thread(new Runnable() {
            @Override
            public void run() {


                try(DatagramSocket clientSocket = new DatagramSocket(50002)) {
                    byte[] buffer = new byte[65507];

                    while(true){
                        DatagramPacket datagramPacket = new DatagramPacket(buffer,0,buffer.length);
                        clientSocket.receive(datagramPacket);
                        ByteArrayInputStream baos = new ByteArrayInputStream(datagramPacket.getData());
                        ObjectInputStream oos = new ObjectInputStream(baos);


                        ButtonMessage receivedMessage = (ButtonMessage) oos.readObject();

                        int x = receivedMessage.lon;
                        int y = receivedMessage.lat;

                        System.out.println(x);

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {

                                System.out.println("initMap(" +x+ ","+y+")");
                                engine.executeScript("set("+y+","+x+")");
                                engine.executeScript("initMap()");
                            }
                        });



                    }

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    listen.start();
    }

    public void ShowAll(ActionEvent event){

       ObservableList<Track> a=table.getItems();
       a.forEach(track ->{

               //System.out.println(track.getLat());

          engine.executeScript(" locationsLat.push("+track.getLat()+")" );
          engine.executeScript(" locationsLon.push("+track.getLon()+")");




       });


       engine.executeScript("initMap2()");

        engine.executeScript("locationsLat= []");
        engine.executeScript("locationsLon= []");

    }





}
