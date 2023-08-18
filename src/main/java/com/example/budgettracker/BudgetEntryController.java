package com.example.budgettracker;

import javafx.collections.FXCollections;
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
    private ComboBox timeFrameCombo;
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
    private TextArea timeEntry;
    @FXML
    private TextArea depositEntry;
    @FXML
    private TextArea incomeEntry;

    @FXML
    private void initialize() {
        expenseButton.setDisable(true);
        onBack(null);

        timeFrameCombo.setItems(FXCollections.observableArrayList("Week(s)", "Month(s)", "Year(s)"));
        incomeCombo.setItems(FXCollections.observableArrayList("Weekly", "Monthly", "Yearly"));
        timeFrameCombo.getSelectionModel().selectFirst();
        incomeCombo.getSelectionModel().selectFirst();

        addNumericListener(savingEntry);
        addNumericListener(timeEntry);
        addNumericListener(incomeEntry);

        savingEntry.textProperty().addListener((observable, oldValue, newValue) -> {
            updateExpenseButtonState();
        });
        timeEntry.textProperty().addListener((observable, oldValue, newValue) -> {
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
        timeEntry.clear();
        depositEntry.clear();
        incomeEntry.clear();
    }
    private void updateExpenseButtonState() {
        if (savingView.isVisible()) {
            boolean hasValidSavingEntry = isNumeric(savingEntry.getText()) && isNumeric(timeEntry.getText());
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
