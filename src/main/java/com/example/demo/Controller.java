package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

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



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lat.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lat"));
        lon.setCellValueFactory(new PropertyValueFactory<Track,Integer>("lon"));
        alt.setCellValueFactory(new PropertyValueFactory<Track,Integer>("alt"));
        status.setCellValueFactory(new PropertyValueFactory<Track,String>("status"));

        table.setItems(list);
        table.getItems().add(new Track(32,32,32,"abc"));


    }


}
