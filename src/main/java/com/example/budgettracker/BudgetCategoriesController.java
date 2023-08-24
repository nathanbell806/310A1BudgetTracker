package com.example.budgettracker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class BudgetCategoriesController {
    //@FXML private Label leftBudgetLabel;
    @FXML private Pane popupPane;
    @FXML private Button addCategoryBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelAddBtn;
    @FXML private Button finishAddBtn;
    @FXML private StackPane overlayPane;
    @FXML private TextField categoryNameField;
    @FXML private TextField budgetedValueField;
    @FXML private VBox categoryList;


    @FXML
    public void initialize(){
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
    }

    @FXML
    public void onAddCategory(){
        popupPane.setVisible(true);
        popupPane.setDisable(false);
        overlayPane.setDisable(false);
        overlayPane.setVisible(true);
    }

    @FXML
    public void onCancelAddCategory(){
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
    }

    @FXML
    public void onFinishAddCategory(){
        //TODO: save info and add list item
        // String catName = categoryNameField.getText();
        // String budgetedValue = budgetedValueField.getText();
        // FXMLLoader fxmlLoader = new FXMLLoader();
        // fxmlLoader.setLocation(getClass().getResource("categoryItem"));
        
        //categoryList.getChildren().add(0, addCategoryBtn);
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
    }
}
