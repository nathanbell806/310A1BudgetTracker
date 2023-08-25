package com.example.budgettracker.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.ArrayList;

public class BudgetOverviewController {
  @FXML
  private PieChart pieChart;

  private ArrayList<Double> data = new ArrayList();


  private void dataGet() {
    data.add(10.0);
    data.add(12.0);
    data.add(1.0);
    data.add(5.0);
    data.add(8.0);
    data.add(15.0);
  }

  @FXML
  public void initialize() {
    dataGet();
    ObservableList<PieChart.Data> budgetData = FXCollections.observableArrayList(
        new PieChart.Data("Everyday", data.get(0)),
        new PieChart.Data("Living",data.get(1)),
        new PieChart.Data("Regular",data.get(2)),
        new PieChart.Data("Irregular",data.get(3)),
        new PieChart.Data("Savings",data.get(4)),
        new PieChart.Data("Personal",data.get(5))
    );
    pieChart.setData(budgetData);
    pieChart.setTitle("Budget Breakdown Pie Chart");
  }
  @FXML
  protected void goBack(){
    //TODO implement after page is made
  }
}