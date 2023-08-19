package com.example.budgettracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class BudgetEntryController {

    @FXML
    private ComboBox savingPeriodCombo;

    @FXML
    private ComboBox savingIncomeCombo;
    @FXML
    private ComboBox incomeCombo;
    @FXML
    private VBox savingView;
    @FXML
    private VBox incomeView;
    @FXML
    private Button expenseButton;
    @FXML
    private Button backButton;
    @FXML
    private HBox optionView;
    @FXML
    private TextArea savingEntry;
    @FXML
    private TextArea savingIncomeEntry;
    @FXML
    private TextArea incomeEntry;

    private ObservableList<String> periodOptions = FXCollections.observableArrayList("Weekly", "Monthly", "Yearly");

    @FXML
    private void initialize() {
        expenseButton.setDisable(true);
        onBack(null);

        savingIncomeCombo.setItems(periodOptions);
        incomeCombo.setItems(periodOptions);
        savingPeriodCombo.setItems(periodOptions);
        savingIncomeCombo.getSelectionModel().selectFirst();
        incomeCombo.getSelectionModel().selectFirst();
        savingPeriodCombo.getSelectionModel().selectFirst();

        addNumericListener(savingEntry);
        addNumericListener(savingIncomeEntry);
        addNumericListener(incomeEntry);

        savingEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            updateExpenseButtonState();
        });
        savingIncomeEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            updateExpenseButtonState();
        });
        incomeEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            updateExpenseButtonState();
        });
    }

    @FXML
    private void onBack(ActionEvent event) {
        clearAllEntries();
        savingView.setVisible(false);
        incomeView.setVisible(false);
        optionView.setVisible(true);
        expenseButton.setVisible(false);
        backButton.setVisible(false);
    }

    @FXML
    private void onExpense(ActionEvent event) {
        // navigate to expense categorise view

        //budget = income - saving goal
    }

    @FXML
    private void onSaving(ActionEvent event) {
        savingView.setVisible(true);
        incomeView.setVisible(false);
        optionView.setVisible(false);
        expenseButton.setVisible(true);
        backButton.setVisible(true);
    }

    @FXML
    private void onIncome(ActionEvent event) {
        incomeView.setVisible(true);
        savingView.setVisible(false);
        optionView.setVisible(false);
        expenseButton.setVisible(true);
        backButton.setVisible(true);
    }

    private void clearAllEntries() {
        savingEntry.clear();
        savingIncomeEntry.clear();
        incomeEntry.clear();
    }
    private void updateExpenseButtonState() {
        if (savingView.isVisible()) {
            boolean hasValidSavingEntry = isNumeric(savingEntry.getText()) && isNumeric(savingIncomeEntry.getText());
            expenseButton.setDisable(!hasValidSavingEntry);
        }
        else if (incomeView.isVisible()) {
            boolean hasValidIncomeEntry = isNumeric(incomeEntry.getText());
            expenseButton.setDisable(!hasValidIncomeEntry);
        }
    }

    private boolean isNumeric(String text) {
        return text.matches("\\d+(\\.\\d+)?");
    }
    private void addNumericListener(TextArea textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

}
