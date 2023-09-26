package com.example.budgettracker.controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

public class BudgetOverviewController {
  @FXML
  private PieChart pieChart;

  @FXML
  private Label totalText;

  @FXML
  private LineChart<Number, Number> lineChart;



  private String username;

  private double totalBudget;
  private ObservableList<PieChart.Data> budgetData;
  private double totalExpense;

  /**
   * Retrieves data from the current profile to be used by initialize in the setup
   * phase
   */
  private void dataGet() {
    Profile profile = CurrentProfile.getInstance().getCurrentProfile();
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    budgetData = FXCollections.observableArrayList();

    totalBudget = profile.getBudget();
    double totalBudgetLeft = totalBudget;
    username = profile.getUsername();

    for (Expense expense : profile.getExpenses()) {
      budgetData.add(new PieChart.Data(expense.getName(), expense.getCost()));
    }
    totalExpense = getTotalExpense(profile);
    totalBudgetLeft = totalBudgetLeft - totalExpense;

    if (totalBudgetLeft > 0) {
      String save = (profile.getSavings() > 0) ? "Extra Savings" : "Savings";
      budgetData.add(new PieChart.Data(save, totalBudgetLeft));
    }

    series.setName("Your Current Spending");
    // Show savings for 10 years
    for (int i = 0; i < 11; i++) {
      // series.getData().add(new XYChart.Data<>(i, (totalBudgetLeft +
      // profile.getSavings()) * i));
      series.getData().add(new XYChart.Data<>(i, forecastSavings(profile, i)));
    }
    lineChart.getData().add(series);
  }

  private double getTotalExpense(Profile profile) {
    return profile.getExpenses().stream().mapToDouble(Expense::getCost).sum();
  }

  private double forecastSavings(Profile profile, int i) {
    double totalBudgetLeft = profile.getBudget() - totalExpense;
    return (totalBudgetLeft) * i * 52;
  }

  /**
   * On startup of FXML this is run to populate piechart and labels
   */
  @FXML
  public void initialize() {
    dataGet();
    totalText.setText("Total budget is $" + totalBudget);
    pieChart.setData(budgetData);
    addEventHandlersCharts();
  }

  /**
   * Sets title and mouse events for charts
   */
  public void addEventHandlersCharts() {
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
          // Set the tooltip text to display the value when the mouse enters the data
          // point
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
   * 
   * @param event form backbutton
   * @throws IOException
   */
  @FXML
  protected void goBack(ActionEvent event) throws IOException {
    ChangeScene changeScene = new ChangeScene();
    changeScene.changeScene(event, SceneName.BUDGET_CATEGORIES);
  }

  /**
   * This method is triggered when the user clicks the "Save Text" button.
   * It initiates a process to save specific income data (From Json file) to a
   * text file.
   * The user is provided with a dialog to select the desired save location.
   *
   * @param event from the "Save Text" button click.
   * @throws IOException if there's an error during the file writing process.
   */
  @FXML
  protected void onSaveText(MouseEvent event) throws IOException {
    // Create a FileChooser object
    FileChooser fileChooser = new FileChooser();

    // Set the Title of the FileChooser dialog window
    fileChooser.setTitle("Export Income Data");

    // Set the type of files the user can save as (e.g. .txt files)
    fileChooser.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"));

    // Show the save dialog window and get the selected file
    File selectedFile = fileChooser.showSaveDialog(null); // Replace null with your main stage if you have it accessible

    // Check if a file was selected
    if (selectedFile != null) {
      // Here you would handle the actual exporting of the income data to the selected
      // file
      // This is just an example, replace with your actual logic
      try (FileWriter fileWriter = new FileWriter(selectedFile)) {
        Profile curProfile = CurrentProfile.getInstance().getCurrentProfile();
        // This is where the data comes in to be saved (Database connection before)
        fileWriter.write("Your income data here:\n");
        fileWriter.write("Budget per week: " + curProfile.getBudget() + "\n");
        fileWriter.write("All Expenses per week: \n");
        for (Expense e : curProfile.getExpenses()) {
          fileWriter.write(e.getName() + " : " + e.getCost() + "\n");
        }
        fileWriter.write("Savings per week: " + curProfile.getSavings() + "\n");
        fileWriter
            .write("Forecasted savings in 10 years: " + forecastSavings(curProfile, 10));
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // Case when user not chose any file
    }
  }

  /**
   * This method is triggered when the user clicks the "Save Image" button.
   * It captures a snapshot of the current state of the user's Pie chart and LineChart.
   * The user is provided with a dialog to select and save image to the desired
   * save location.
   *
   * @param event from the "Save Image" button click.
   * @throws IOException if there's an error during the image saving process.
   */
  @FXML
  protected void onSaveImage(MouseEvent event) throws IOException {
    // 1. Snapshot each chart individually
    WritableImage lineChartImage = lineChart.snapshot(new SnapshotParameters(), null);
    WritableImage pieChartImage = pieChart.snapshot(new SnapshotParameters(), null);

    // 2. Combine the images programmatically (horizontally)
    int combinedWidth = (int) (lineChartImage.getWidth() + pieChartImage.getWidth());
    int height = (int) Math.max(pieChartImage.getHeight(), lineChartImage.getHeight());

    BufferedImage combinedImage = new BufferedImage(combinedWidth, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g = combinedImage.createGraphics();

    g.drawImage(SwingFXUtils.fromFXImage(lineChartImage, null), 0, 0, null);
    g.drawImage(SwingFXUtils.fromFXImage(pieChartImage, null), (int) lineChartImage.getWidth(), 0, null);
    g.dispose();

    // 3. Save the combined image
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Export Image as PNG");
    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
    File selectedFile = fileChooser.showSaveDialog(null);

    if (selectedFile != null) {
      ImageIO.write(combinedImage, "png", selectedFile);
    } else {
      // Handle no file chosen
    }
  }
}