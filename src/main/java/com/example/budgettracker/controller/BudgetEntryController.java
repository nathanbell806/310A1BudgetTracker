package com.example.budgettracker.controller;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Map;

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
    @FXML
    private ImageView profileIcon;
    @FXML
    private ComboBox<String> currencyComboBox;

    private static final String MONTHLY = "Monthly";
    private static final String YEARLY = "Yearly";

    private final ObservableList<String> periodOptions = FXCollections.observableArrayList("Weekly", MONTHLY, YEARLY);

    ChangeScene changeScene;

    @FXML
    public void initialize() {
        changeScene = new ChangeScene();
        currencyComboBox.getItems().addAll("US", "KR", "EU", "UK", "JP");
        currencyComboBox.setValue("US");  // Default value

        expenseButton.setDisable(true);
        onBack(null);

        savingIncomeCombo.setItems(periodOptions);
        incomeCombo.setItems(periodOptions);
        savingPeriodCombo.setItems(periodOptions);
        savingIncomeCombo.getSelectionModel().selectFirst();
        incomeCombo.getSelectionModel().selectFirst();
        savingPeriodCombo.getSelectionModel().selectFirst();

        if (CurrentProfile.getInstance().getCurrentProfile().getProfilePicture() != null) {
            Image image = new Image("file:" + CurrentProfile.getInstance().getCurrentProfile().getProfilePicture());
            profileIcon.setImage(image);
        }

        // Enable or Disable the expense navigation button if required entries are
        // filled and is valid
        addNumericListener(savingEntry);
        addNumericListener(savingIncomeEntry);
        addNumericListener(incomeEntry);
        savingEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
        savingIncomeEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
        incomeEntry.textProperty().addListener((observable, oldValue, newValue) -> updateExpenseButtonState());
    }
    private final Map<String, Double> conversionRates = Map.of(
            "US", 1.0,
            "KR", 1100.0,
            "EU", 0.85,
            "UK", 0.75,
            "JP", 105.0
    );

    private double convertAmount(double amount, String fromCurrency, String toCurrency) {
        double fromRate = conversionRates.getOrDefault(fromCurrency, 1.0);
        double toRate = conversionRates.getOrDefault(toCurrency, 1.0);
        return (amount / fromRate) * toRate;
    }

    /**
     * This method navigates from the 'Amount to Budget' or 'Set Saving Goals' back
     * to the option selector view.
     *
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
     * This method navigates to the category expenses scene.
     *
     * @param event The on click event
     */
    @FXML
    public void onExpense(ActionEvent event) throws IOException {

        // Get the current currency from the user's profile and the selected currency from the ComboBox
        String currentCurrency = CurrentProfile.getInstance().getCurrentProfile().getCurrentCurrency();
        String selectedCurrency = currencyComboBox.getValue();

        // Save the new user data
        saveUserEntryData(currentCurrency, selectedCurrency);

        // navigate to expense categorise view
        changeScene.changeScene(event, SceneName.BUDGET_CATEGORIES);
    }

    /**
     * This method navigates to the Profile Select Scene
     *
     * @param event The mouse on click event
     * @throws IOException
     */
    @FXML
    public void onProfileSelect(MouseEvent event) throws IOException {
        changeScene.changeScene(event, SceneName.SELECT_PROFILE);
    }

    /**
     * This helped method saves the user data depending on current tab to the JSON
     * always in weekly format
     */
    private void saveUserEntryData(String fromCurrency, String toCurrency) throws IOException {

        for (Expense expense : CurrentProfile.getInstance().getCurrentProfile().getExpenses()) {
            expense.setCost(convertAmount(expense.getCost(), fromCurrency, toCurrency));
        }

        // checks which page was open
        if (!savingIncomeEntry.getText().isEmpty()) {
            int income = Integer.parseInt(savingIncomeEntry.getText());
            int saving = Integer.parseInt(savingEntry.getText());

            String incomePeriod = savingIncomeCombo.getValue();
            String savingPeriod = savingPeriodCombo.getValue();

            // convert to always want data weekly
            if (incomePeriod.equals(MONTHLY)) {
                income = (income * 12) / 52;
            } else if (incomePeriod.equals(YEARLY)) {
                income = income / 52;
            }

            if (savingPeriod.equals(MONTHLY)) {
                saving = (saving * 12) / 52;
            } else if (savingPeriod.equals(YEARLY)) {
                saving = saving / 52;
            }

            // budget = income - saving goal
            CurrentProfile.getInstance().getCurrentProfile().setBudget((int) convertAmount(income-saving, fromCurrency, toCurrency));
            CurrentProfile.getInstance().getCurrentProfile().setIncome((int) convertAmount(income, fromCurrency, toCurrency));
            CurrentProfile.getInstance().getCurrentProfile().setSavings((int) convertAmount(saving, fromCurrency, toCurrency));
        } else {
            int income = Integer.parseInt(incomeEntry.getText());

            String budgetPeriod = incomeCombo.getValue();

            // convert to always weekly data
            if (budgetPeriod.equals(MONTHLY)) {
                income = (income * 12) / 52;
            } else if (budgetPeriod.equals(YEARLY)) {
                income = income / 52;
            }

            CurrentProfile.getInstance().getCurrentProfile().setBudget(income);
            CurrentProfile.getInstance().getCurrentProfile().setIncome(income);
            CurrentProfile.getInstance().getCurrentProfile().setSavings(0);

        }

        // Set the selected currency to the current profile's currentCurrency
        CurrentProfile.getInstance().getCurrentProfile().setCurrentCurrency(toCurrency);

        ProfileRepository profileRepository = new ProfileRepository();
        profileRepository.saveProfile(CurrentProfile.getInstance().getCurrentProfile());
    }

    /**
     * This method shows the 'Set Saving Goals' view.
     *
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
     *
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
        } else if (incomeView.isVisible()) {
            boolean hasValidIncomeEntry = isNumeric(incomeEntry.getText());
            expenseButton.setDisable(!hasValidIncomeEntry);
        }
    }

    /**
     * This method takes a String and checks if it is numeric
     *
     * @param text The String to check numeric for
     * @return boolean
     */
    private boolean isNumeric(String text) {
        return text.matches("\\d+(\\.\\d+)?");
    }

    /**
     * This method takes a TextArea and adds non-numeric characters preventer
     * listener
     *
     * @param textField The TextArea to add the numeric listener to
     */
    private void addNumericListener(TextArea textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * This method takes the user to the edit profile scene
     *
     * @param event The mouse on click event
     * @throws IOException If the scene cannot be changed
     */
    @FXML
    public void onProfileIconClick(MouseEvent event) throws IOException {
        changeScene.changeScene(event, SceneName.EDIT_PROFILE);
    }
}
