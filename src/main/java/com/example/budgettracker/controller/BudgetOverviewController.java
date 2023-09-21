package com.example.budgettracker.controller;

import java.io.IOException;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class BudgetOverviewController {
  @FXML
  private PieChart pieChart;

  @FXML
  private Label usernameText;

  @FXML
  private Label totalText;

  @FXML
  private LineChart<Number, Number> lineChart;

  @FXML
  private Button toggleButton;

  private String username;

  private double totalBudget;
  private ObservableList<PieChart.Data> budgetData;

  /**
   * Retrieves data from the current profile to be used by initialize in the setup phase
   */
  private void dataGet() {
    Profile profile = CurrentProfile.getInstance().getCurrentProfile();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    budgetData = FXCollections.observableArrayList();

    totalBudget = profile.getBudget();
    double totalBudgetLeft = totalBudget;
    username = profile.getUsername();

    for (Expense expense: profile.getExpenses()) {
          budgetData.add(new PieChart.Data(expense.getName(), expense.getCost()));
          totalBudgetLeft = totalBudgetLeft - expense.getCost();
    }

    if(totalBudgetLeft > 0){
      String save = (profile.getSavings() > 0) ? "Extra Savings" : "Savings";
      budgetData.add(new PieChart.Data(save, totalBudgetLeft));
    }


    series.setName("Your Current Spending");
    // Show savings for 10 years
    for (int i = 0; i < 11; i++) {
      series.getData().add(new XYChart.Data<>(i, (totalBudgetLeft + profile.getSavings()) * i));
    }
    lineChart.getData().add(series);
  }

  /**
   * On startup of FXML this is run to populate piechart and labels
   */
  @FXML
  public void initialize() {
    dataGet();

    usernameText.setText(username);
    totalText.setText("Total budget is $" + totalBudget);
    pieChart.setData(budgetData);
    addEventHandlersCharts();
    // Handle the toggle button click event
    toggleButton.setOnAction(event -> {
      // Toggle visibility of charts
      pieChart.setVisible(!pieChart.isVisible());
      lineChart.setVisible(!lineChart.isVisible());
    });
  }

  /**
   * Sets title and mouse events for charts
   */
  public void addEventHandlersCharts(){
    pieChart.setTitle("Budget Breakdown Pie Chart");
    // Add event handlers to each pie chart slice
    Tooltip tooltip = new Tooltip();
    tooltip.setStyle("-fx-font-size: 20");
    for (PieChart.Data data : pieChart.getData()) {
      data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
        if (!tooltip.isShowing()) {
          // Set the tooltip text to display the value when the mouse enters the slice
          tooltip.setText("$" + String.valueOf(data.getPieValue()));
          tooltip.show(data.getNode(), e.getScreenX() + 10, e.getScreenY() + 10);
        }
      });
      data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
        // Hide the tooltip when the mouse exits the slice
        tooltip.hide();
      });
    }
    // Add event handlers to each data series in the LineChart
    for (XYChart.Series<Number, Number> series : lineChart.getData()) {
      for (XYChart.Data<Number, Number> data : series.getData()) {
        data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
          // Set the tooltip text to display the value when the mouse enters the data point
          String tooltipText = "$" + data.getYValue();
          tooltip.setText(tooltipText);

          // Show the tooltip immediately
          tooltip.show(data.getNode(), e.getScreenX() + 10, e.getScreenY() + 10);
        });

        data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
          // Hide the tooltip when the mouse exits the data point
          tooltip.hide();
        });
      }
    }
  }

  /**
   * This method is activated on click of back button
   * @param event form backbutton
   * @throws IOException
   */
  @FXML
  protected void goBack(ActionEvent event) throws IOException {
    ChangeScene changeScene = new ChangeScene();
    changeScene.changeScene(event, SceneName.BUDGET_CATEGORIES);
  }
}