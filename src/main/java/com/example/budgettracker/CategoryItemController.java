package com.example.budgettracker;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    CurrentProfile currentProfile;
    ProfileRepository profileRepository;

    public void setData(String categoryNameData, String budgetedValueData){
        categoryName.setText(categoryNameData);
        try {
            int intBudgetedValue = Integer.parseInt(budgetedValueData);
            budgetedValue.setText("$" + intBudgetedValue);
        } catch (NumberFormatException e) {
            double doubleBudgetedData = Double.parseDouble(budgetedValueData);
            budgetedValue.setText("$" + (int)doubleBudgetedData);

        }
    }

    public void onDelete(){
        VBox parentContainer = (VBox) deleteBtn.getParent().getParent();
        parentContainer.getChildren().remove(deleteBtn.getParent());

        List<Expense> expenses = currentProfile.getCurrentProfile().getExpenses();
        int index =0;
        ArrayList<Integer> indexes = new ArrayList<>();
        for (Expense expense : expenses){
            if (("$"+(int)expense.getCost()).equals(budgetedValue.getText())){
                //for some reason when trying to remove the expense here it led to a java reflection exception so instead we can just get the indexes of which expenses to remove and remove them after
                indexes.add(index);
                break;
            }
            index = index+1;
        }
        for(int expenseIndex: indexes){
            expenses.remove(expenseIndex);
        }
        currentProfile.getCurrentProfile().setExpenses(expenses);
        profileRepository.saveProfile(currentProfile.getCurrentProfile());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            profileRepository = new ProfileRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentProfile = CurrentProfile.getInstance();

    }

}

