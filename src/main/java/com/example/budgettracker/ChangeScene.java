package com.example.budgettracker;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScene {

    public ChangeScene(){
        //public constructor
    }

    public void changeScene(Event event, String destination) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(destination));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent, 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }


}
