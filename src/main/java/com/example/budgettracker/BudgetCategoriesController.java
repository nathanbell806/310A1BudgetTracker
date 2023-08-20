package com.example.budgettracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class BudgetCategoriesController {
    @FXML private Label leftBudgetLabel;
    @FXML private Pane popupPane;
    @FXML private Button addCategoryBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelAddBtn;
    @FXML private Button finishAddBtn;
    @FXML private StackPane overlay;
    @FXML private TextField categoryNameField;


    @FXML
    public void initialize(){
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlay.setDisable(true);
        overlay.setVisible(false);
    }

    @FXML
    public void onAddCategory(){
        popupPane.setVisible(true);
        popupPane.setDisable(false);
        overlay.setDisable(false);
        overlay.setVisible(true);
    }

    @FXML
    public void onCancelAddCategory(){
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlay.setDisable(true);
        overlay.setVisible(false);
    }

    @FXML
    public void onFinishAddCategory(){
        //TODO: save info and add list item
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlay.setDisable(true);
        overlay.setVisible(false);
    }
}
