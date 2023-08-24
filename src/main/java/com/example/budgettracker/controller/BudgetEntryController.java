package com.example.budgettracker.controller;

import java.io.IOException;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.ProfileFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BudgetEntryController {

    @FXML
    private ComboBox<String> savingPeriodCombo;
    @FXML
    private ComboBox<String> savingIncomeCombo;
    @FXML
    private ComboBox<String> incomeCombo;
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
    private final ObservableList<String> periodOptions = FXCollections.observableArrayList("Weekly", "Monthly", "Yearly");
    
    ChangeScene changeScene;

    @FXML
    public void initialize() {
        changeScene = new ChangeScene();

        expenseButton.setDisable(true);
        onBack(null);
        
        savingIncomeCombo.setItems(periodOptions);
        incomeCombo.setItems(periodOptions);
        savingPeriodCombo.setItems(periodOptions);
        savingIncomeCombo.getSelectionModel().selectFirst();
        incomeCombo.getSelectionModel().selectFirst();
        savingPeriodCombo.getSelectionModel().selectFirst();

        // Enable or Disable the expense navigation button if required entries are filled and is valid
        addNumericListener(savingEntry);
        addNumericListener(savingIncomeEntry);
        addNumericListener(incomeEntry);
        savingEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
        savingIncomeEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
        incomeEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
    }

    /**
     * This method navigates from the 'Amount to Budget' or 'Set Saving Goals' back to the option selector view.
     * @param event The on click event
     */
    @FXML
    public void onBack(ActionEvent event) {
        clearAllEntries();
        savingView.setVisible(false);
        incomeView.setVisible(false);
        optionView.setVisible(true);
        expenseButton.setVisible(false);
        backButton.setVisible(false);
    }

    /**
     * This method navigates to the categorise expenses scene.
     * @param event The on click event
     */
    @FXML
    public void onExpense(ActionEvent event) throws IOException {

        saveUserEntryData();
        // navigate to expense categorise view
        changeScene.changeScene(event,SceneName.BUDGET_CATEGORIES);
    }

    /**
     * This helped method saves the user data depending on current tab to the JSON always in weekly format
     */
    private void saveUserEntryData() throws IOException {
        //checks which page was open
        if(!savingIncomeEntry.getText().isEmpty()){
            int income = Integer.parseInt(savingIncomeEntry.getText());
            int saving = Integer.parseInt(savingEntry.getText());

            String incomePeriod = savingIncomeCombo.getValue();
            String savingPeriod = savingPeriodCombo.getValue();

            //convert to always want data weekly
            if(incomePeriod.equals("Monthly")){
                income = (income*12)/52;
            } else if (incomePeriod.equals("Yearly")) {
                income = income/52;
            }

            if(savingPeriod.equals("Monthly")){
                saving = (saving*12)/52;
            } else if (savingPeriod.equals("Yearly")) {
                saving = saving/52;
            }

            //budget = income - saving goal
            CurrentProfile.getInstance().getCurrentProfile().setBudget(income - saving);
            CurrentProfile.getInstance().getCurrentProfile().setSavings(saving);
        } else {
            int income = Integer.parseInt(incomeEntry.getText());

            String budgetPeriod = incomeCombo.getValue();

            //convert to always weekly data
            if(budgetPeriod.equals("Monthly")){{
                income = (income*12)/52;
            }} else if (budgetPeriod.equals("Yearly")) {
                income = income/52;
            }

            CurrentProfile.getInstance().getCurrentProfile().setBudget(income);
            CurrentProfile.getInstance().getCurrentProfile().setSavings(0);

        }
        ProfileFactory profileFactory = new ProfileFactory();
        profileFactory.saveProfile(CurrentProfile.getInstance().getCurrentProfile());
    }

    /**
     * This method shows the 'Set Saving Goals' view.
     * @param event The on click event
     */
    @FXML
    public void onSaving(ActionEvent event) {
        savingView.setVisible(true);
        incomeView.setVisible(false);
        optionView.setVisible(false);
        expenseButton.setVisible(true);
        backButton.setVisible(true);
    }

    /**
     * This method shows the 'Amount to Budget' view.
     * @param event The on click event
     */
    @FXML
    public void onIncome(ActionEvent event) {
        incomeView.setVisible(true);
        savingView.setVisible(false);
        optionView.setVisible(false);
        expenseButton.setVisible(true);
        backButton.setVisible(true);
    }

    /**
     * This method clears all TextArea views.
     */
    private void clearAllEntries() {
        savingEntry.clear();
        savingIncomeEntry.clear();
        incomeEntry.clear();
    }

    /**
     * This method disables/enables the categorise expense navigation button.
     */
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

    /**
     * This method takes a String and checks if it is numeric
     * @param text The String to check numeric for
     * @return boolean
     */
    private boolean isNumeric(String text) {
        return text.matches("\\d+(\\.\\d+)?");
    }

    /**
     * This method takes a TextArea and adds non-numeric characters preventer listener
     * @param textField The TextArea to add the numeric listener to
     */
    private void addNumericListener(TextArea textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

}
