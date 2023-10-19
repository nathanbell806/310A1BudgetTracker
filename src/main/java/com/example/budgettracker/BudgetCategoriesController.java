package com.example.budgettracker;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.controller.CategoryItemController;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.Profile;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import com.example.budgettracker.profiles.CurrentProfile;
import javafx.scene.text.Text;

public class BudgetCategoriesController {

    @FXML
    private Label leftBudgetLabel;
    @FXML
    private Pane popupPane;
    @FXML
    private Button addCategoryBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelAddBtn;
    @FXML
    private Button finishAddBtn;
    @FXML
    private StackPane overlayPane;
    @FXML
    private TextField categoryNameField;
    @FXML
    private TextField budgetValueField;
    @FXML
    private VBox categoryList;
    @FXML
    private Text totalBudgetTxt;

    private float leftBudgetValue = CurrentProfile.getInstance().getCurrentProfile().getBudget();
    private float totalBudgeted;

    private final ChangeScene changeScene = new ChangeScene();
    private double totalExpense = 0;

    CurrentProfile currentProfile;

    ProfileRepository profileRepository;

    @FXML
    public void initialize() throws IOException {
        currentProfile = CurrentProfile.getInstance();
        profileRepository = new ProfileRepository();
        popupPane.setVisible(false);
        popupPane.setDisable(true);
        leftBudgetLabel.setText(String.format("%.2f", leftBudgetValue) + currentProfile.getCurrentProfile().getCurrencySymbol());
        categoryList.getChildren().addListener((ListChangeListener<Node>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    HBox removedItem = (HBox) change.getRemoved().get(0);
                    for (Node node : removedItem.getChildren()) {
                        if (node instanceof Label && node.getId().equals("budgetedValue")) {
                            updateLeftBudget(((Label) node).getText().replace(currentProfile.getCurrentProfile().getCurrencySymbol(), ""), true);
                            updateTotalBudgeted(((Label) node).getText().replace(currentProfile.getCurrentProfile().getCurrencySymbol(), ""), false);
                        }
                    }
                } else if (change.wasAdded()) {
                    HBox addedItem = (HBox) change.getAddedSubList().get(0);
                    for (Node node : addedItem.getChildren()) {
                        if (node instanceof Label && node.getId().equals("budgetedValue")) {
                            updateLeftBudget(((Label) node).getText().replace(currentProfile.getCurrentProfile().getCurrencySymbol(), ""), false);
                            updateTotalBudgeted(((Label) node).getText().replace(currentProfile.getCurrentProfile().getCurrencySymbol(), ""), true);
                        }
                    }
                }
            }
        });
        for (Expense expense : currentProfile.getCurrentProfile().getExpenses()) {
            initialiseCategory(expense.getName(), "" + (int) expense.getCost());
        }

    }

    @FXML
    public void onProfileSelect(MouseEvent event) throws IOException {
        changeScene.changeScene(event, SceneName.BUDGET_ENTRY);
    }

    @FXML
    public void onAddCategory() {
        popupPane.setVisible(true);
        popupPane.setDisable(false);
    }

    @FXML
    public void onCancelAddCategory() {
        popupPane.setVisible(false);
        popupPane.setDisable(true);
    }

    @FXML
    public void onFinishAddCategory() {
        try {
            String catName = categoryNameField.getText();
            String budgetedValue = budgetValueField.getText();
            double doubleValue = Double.parseDouble(budgetedValue);
            Expense expense = new Expense(catName, (int) doubleValue);
            CurrentProfile.getInstance().getCurrentProfile().addExpense(expense);
            profileRepository.saveProfile(currentProfile.getCurrentProfile());
            totalExpense += expense.getCost();
            initialiseCategory(catName, budgetedValue);

            popupPane.setVisible(false);
            popupPane.setDisable(true);

        } catch (NumberFormatException ex) {
            showAlert("nonNumber", "input is not a number");
        }
    }

    public void initialiseCategory(String catName, String budgetedValue) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("categoryItem.fxml"));
        HBox hBox;
        try {
            hBox = fxmlLoader.load();
            CategoryItemController catItemController = fxmlLoader.getController();

            catItemController.setData(catName, budgetedValue);

            categoryList.getChildren().add(hBox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateLeftBudget(String budgetedValue, boolean isIncrement) {
        if (isIncrement) {
            leftBudgetValue += Float.parseFloat(budgetedValue);
        } else {
            leftBudgetValue -= Float.parseFloat(budgetedValue);
        }
        leftBudgetLabel.setText(currentProfile.getCurrentProfile().getCurrencySymbol() + String.format("%.2f", leftBudgetValue));
        if (leftBudgetValue < 0) {
            showAlert("overBudget", "Expenses are greater than budget");
        }
    }

    public void updateTotalBudgeted(String budgetedValue, boolean isIncrement) {
        if (isIncrement) {
            totalBudgeted += Float.parseFloat(budgetedValue);
        } else {
            totalBudgeted -= Float.parseFloat(budgetedValue);
        }
    }

    public void onSummary(ActionEvent actionEvent) throws IOException {
        Profile curProfile = CurrentProfile.getInstance().getCurrentProfile();
        // saving profile so that the savins is updated
        curProfile.setSavings((int) (curProfile.getBudget() - totalExpense));
        profileRepository.saveProfile(curProfile);
        changeScene.changeScene(actionEvent, SceneName.BUDGET_OVERVIEW);
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
