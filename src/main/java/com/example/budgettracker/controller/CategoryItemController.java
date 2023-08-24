package com.example.budgettracker.controller;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CategoryItemController implements Initializable{
        @FXML
    private Label budgetedValue;

    @FXML
    private Label categoryName;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button editBtn;

    public void setData(String budgetedValueData, String categoryNameData){
        categoryName.setText(categoryNameData);
        budgetedValue.setText(budgetedValueData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
    }
}
