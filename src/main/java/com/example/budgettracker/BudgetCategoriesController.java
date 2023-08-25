package com.example.budgettracker;

import com.example.budgettracker.profiles.Expense;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import com.example.budgettracker.profiles.CurrentProfile;

public class BudgetCategoriesController {
    @FXML private Label leftBudgetLabel;
    @FXML private Pane popupPane;
    @FXML private Button addCategoryBtn;
    @FXML private Button saveBtn;
    @FXML private Button cancelAddBtn;
    @FXML private Button finishAddBtn;
    @FXML private StackPane overlayPane;
    @FXML private TextField categoryNameField;
    @FXML private TextField budgetValueField;
    @FXML private VBox categoryList;

    private float leftBudgetValue = CurrentProfile.getInstance().getCurrentProfile().getBudget();

    private final ChangeScene changeScene = new ChangeScene();

    CurrentProfile currentProfile;

    @FXML
    public void initialize(){
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
        leftBudgetLabel.setText(String.format("%.2f", leftBudgetValue) + "$");
        categoryList.getChildren().addListener((ListChangeListener<Node>) change ->{
            while(change.next()){
            if(change.wasRemoved()){
                HBox removedItem = (HBox) change.getRemoved().get(0);
                for (Node node : removedItem.getChildren()) {
                    if(node instanceof Label && node.getId().equals("budgetedValue")){
                        updateLeftBudget(((Label)node).getText().replace("$", ""), true);
                    }
            }
        }else if (change.wasAdded()){
            HBox addedItem = (HBox) change.getAddedSubList().get(0);
            for (Node node : addedItem.getChildren()) {
                    if(node instanceof Label && node.getId().equals("budgetedValue")){
                        updateLeftBudget(((Label)node).getText().replace("$", ""), false);
                    }
            }
        }
    }});
        currentProfile = CurrentProfile.getInstance();
        for(Expense expense: currentProfile.getCurrentProfile().getExpenses()){
            initialiseCategory(expense.getName(),""+expense.getCost());
        }


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
        Expense expense = new Expense(catName, Integer.parseInt(budgetedValue));
        CurrentProfile.getInstance().getCurrentProfile().addExpense(expense);

        initialiseCategory(catName, budgetedValue);

        popupPane.setVisible(false);
        popupPane.setDisable(true);
        overlayPane.setDisable(true);
        overlayPane.setVisible(false);
    }

    public void initialiseCategory(String catName, String budgetedValue){

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
    }


    public void updateLeftBudget(String budgetedValue, boolean isIncrement){
        if(isIncrement){
            leftBudgetValue += Float.parseFloat(budgetedValue);
        } else{
            leftBudgetValue -= Float.parseFloat(budgetedValue);
        }
        leftBudgetLabel.setText(String.format("%.2f", leftBudgetValue) + "$");
    }

    public void onSummary(ActionEvent actionEvent) throws IOException {

        changeScene.changeScene(actionEvent,SceneName.BUDGET_OVERVIEW);
    }
}
