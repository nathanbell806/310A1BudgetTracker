package com.example.budgettracker.controller;

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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BudgetOverviewController {
  @FXML
  private PieChart pieChart;

  @FXML
  private Label usernameText;

  @FXML
  private Label totalText;

  private String username;

  private double totalBudget;

  private WritableImage image;

  private ObservableList<PieChart.Data> budgetData;

  /**
   * Retrieves data from the current profile to be used by initialize in the setup phase
   */
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

  /**
   * On startup of FXML this is run to populate piechart and labels
   */
  @FXML
  public void initialize() {
    dataGet();

    usernameText.setText(username);
    totalText.setText("Total budget is " + totalBudget);
    pieChart.setData(budgetData);
    pieChart.setTitle("Budget Breakdown Pie Chart");
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

  @FXML
  protected  void onSaveText(MouseEvent event) throws  IOException{
    // Create a FileChooser object
    FileChooser fileChooser = new FileChooser();

    // Set the Title of the FileChooser dialog window
    fileChooser.setTitle("Export Income Data");

    // Set the type of files the user can save as (e.g. .txt files)
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
    );

    // Show the save dialog window and get the selected file
    File selectedFile = fileChooser.showSaveDialog(null); // Replace null with your main stage if you have it accessible

    // Check if a file was selected
    if (selectedFile != null) {
      // Here you would handle the actual exporting of the income data to the selected file
      // This is just an example, replace with your actual logic
      try (FileWriter fileWriter = new FileWriter(selectedFile)) {

        // This is where the data comes in to be saved (Database connection before)
        fileWriter.write("Your income data here...");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      // Case when user not chose any file
    }
  }

  @FXML
  protected void onSaveImage(MouseEvent event) throws IOException {
    // Create the snapshot
    WritableImage image = pieChart.snapshot(new SnapshotParameters(), null);

    // Save the snapshot with ImageIO
    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

    // Create a FileChooser object
    FileChooser fileChooser = new FileChooser();

    // Set the Title of the FileChooser dialog window
    fileChooser.setTitle("Export Image as PNG");

    // Set the type of files the user can save as (.png files)
    fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("PNG Image", "*.png")
    );

    // Show the save dialog window and get the selected file
    File selectedFile = fileChooser.showSaveDialog(null);  // Replace null with your main stage if you have it accessible

    // Check if a file was selected
    if (selectedFile != null) {
      ImageIO.write(bufferedImage, "png", selectedFile);
    } else {
      // Handle the case where no file was chosen
    }
  }
}