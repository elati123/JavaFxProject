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

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public static Controller single_instance = null;

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

        addData();
    }
    public void addData() {

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
                            table.getItems().add(new Track(32,11,43,"wer"));
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

    }

}
