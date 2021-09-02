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

public class HelloController implements Initializable {

    @FXML
    public TableView<track> table;

    @FXML
    public TableColumn<track, Integer> lat;

    @FXML
    public TableColumn<track,Integer> lon;

    @FXML
    public TableColumn<track, Integer> alt;

    @FXML
    public TableColumn<track, String> status;

    @FXML
    public Button exit;

    ObservableList<track> list = FXCollections.observableArrayList(
            new track(21,1,12,"e")

    );



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lat.setCellValueFactory(new PropertyValueFactory<track,Integer>("lat"));
        lon.setCellValueFactory(new PropertyValueFactory<track,Integer>("lon"));
        alt.setCellValueFactory(new PropertyValueFactory<track,Integer>("alt"));
        status.setCellValueFactory(new PropertyValueFactory<track,String>("status"));

        table.setItems(list);
        table.getItems().add(new track(32,32,32,"abc"));


    }


}
