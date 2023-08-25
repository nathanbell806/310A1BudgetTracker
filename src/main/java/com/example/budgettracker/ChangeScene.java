package com.example.budgettracker;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChangeScene {
    Parent parent;
    public ChangeScene(){
        //public constructor
    }

    public void changeScene(Event event, SceneName destination) throws IOException {

        switch(destination){
            case SELECT_PROFILE:
                setParent(FXMLLoader.load(getClass().getResource("/com/example/budgettracker/select-profile.fxml")));
                break;
            case CREATE_PROFILE:
                setParent(FXMLLoader.load(getClass().getResource("/com/example/budgettracker/create-profile.fxml")));
                break;
            case BUDGET_ENTRY:
                setParent(FXMLLoader.load(getClass().getResource("/com/example/budgettracker/budget-entry.fxml")));
                break;
            case BUDGET_CATEGORIES:
                setParent(FXMLLoader.load(getClass().getResource("/com/example/budgettracker/budget-categories.fxml")));
                break;
            case BUDGET_OVERVIEW:
                setParent(FXMLLoader.load(getClass().getResource("/com/example/budgettracker/budget-overview.fxml")));
                break;

        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(this.parent, 1920, 1080);
        stage.setScene(scene);
        stage.show();
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
