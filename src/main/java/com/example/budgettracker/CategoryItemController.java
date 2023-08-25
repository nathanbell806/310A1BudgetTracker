package com.example.budgettracker;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryItemController implements Initializable{
        @FXML
    private Label budgetedValue;

    @FXML
    private Label categoryName;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    public void setData(String categoryNameData, String budgetedValueData){
        categoryName.setText(categoryNameData);
        budgetedValue.setText("$" + budgetedValueData);
    }

    public void onDelete(){
        VBox parentContainer = (VBox) deleteBtn.getParent().getParent();
        parentContainer.getChildren().remove(deleteBtn.getParent());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }
}

