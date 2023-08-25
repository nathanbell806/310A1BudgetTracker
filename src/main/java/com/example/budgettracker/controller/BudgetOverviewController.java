package com.example.budgettracker.controller;

import java.util.ArrayList;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class BudgetOverviewController {
  @FXML
  private PieChart pieChart;

  private String username;

  private double totalBudget;
  private ObservableList<PieChart.Data> budgetData;


  private void dataGet() {
    Profile profile = CurrentProfile.getInstance().getCurrentProfile();
    budgetData = FXCollections.observableArrayList();

    totalBudget = profile.getBudget();
    double totalBudgetLeft = totalBudget;
    username = profile.getUsername();

    for (Expense expense: profile.getExpenses()) {
          budgetData.add(new PieChart.Data(expense.getName(), expense.getCost()));
          totalBudgetLeft = totalBudgetLeft - expense.getCost();
    }

    if(totalBudgetLeft > 0){
      budgetData.add(new PieChart.Data("Savings", totalBudgetLeft));
    }



  }

  @FXML
  public void initialize() {
    dataGet();

    pieChart.setData(budgetData);
    pieChart.setTitle("Budget Breakdown Pie Chart");
  }
  @FXML
  protected void goBack(){
    //TODO implement after page is made
  }
}