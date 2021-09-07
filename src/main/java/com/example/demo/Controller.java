package com.example.demo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller single_instance = null;

    public int flag= 0;

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
    public Button exit;

    ObservableList<Track> list = FXCollections.observableArrayList(
            new Track(21,1,12,"e")

    );

    public Controller(){
        list.add(new Track(0,0,0,"initial"));
    }
    public static Controller getInstance(){
        if (single_instance == null)
            single_instance = new Controller();

        return single_instance;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lat.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lat"));
        lon.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lon"));
        alt.setCellValueFactory(new PropertyValueFactory<Track,Integer>("alt"));
        status.setCellValueFactory(new PropertyValueFactory<Track,String>("status"));

        table.setItems(list);
        table.getItems().add(new Track(32,32,32,"abc"));
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





    }
    //Deno function for table functionality
    public  void addData(int lon,int lat, int alt , String status) {

        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                double progress = 0;
               while (true){

                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            table.getItems().add(new Track(lon,lat,alt,status));


                        }
                    });
                }

            }
        });

        task.start();

    }



    public void close(ActionEvent event){

        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
        // Terminate all threads
        flag = 1;


    }
    // Function that adds data it listens
    public void listen(int port){

        Thread listen = new Thread(new Runnable() {
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





}
