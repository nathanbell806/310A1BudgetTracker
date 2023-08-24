package com.example.budgettracker;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
    @FXML private TextField budgetValueField;
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
        String catName = categoryNameField.getText();
        String budgetedValue = budgetValueField.getText();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("categoryItem.fxml"));
        HBox hBox;
        try {
            hBox = fxmlLoader.load();
            CategoryItemController catItemController = fxmlLoader.getController();
            catItemController.setData(catName, budgetedValue);
            categoryList.getChildren().add(hBox);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
    }
}
