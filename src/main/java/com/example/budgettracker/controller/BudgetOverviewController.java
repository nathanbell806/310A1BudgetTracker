package com.example.budgettracker.controller;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
     * On startup of FXML this is run to populate piechart and labels
     */
    @FXML
    public void initialize() {
        ChartController chartController = new ChartController(pieChart, lineChart, new Tooltip());
        chartController.initialize();
        totalText.setText("Total budget is " + CurrentProfile.getInstance().getCurrentProfile().getCurrencySymbol() + chartController.getTotalBudget());
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
                new FileChooser.ExtensionFilter("CSV Files (*.csv)", "*.csv"));

        // Show the save dialog window and get the selected file
        File selectedFile = fileChooser.showSaveDialog(null); // Replace null with your main stage if you have it accessible

        // Check if a file was selected
        if (selectedFile != null) {
            // Here you would handle the actual exporting of the income data to the selected
            // file
            // This is just an example, replace with your actual logic
            try {
                writeToCSV(lineChart.getData().get(0).getData(), selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Case when user not chose any file
        }
    }

    private void writeToCSV(ObservableList<XYChart.Data<Number, Number>> data, File file) throws IOException {
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("Years");
        csvWriter.append(",");
        csvWriter.append("Forecasted savings");
        csvWriter.append("\n");

        // Write data rows
        for (XYChart.Data<Number, Number> point : data) {
            Number xValue = point.getXValue();
            Number yValue = point.getYValue();

            csvWriter.append(xValue.toString());
            csvWriter.append(",");
            csvWriter.append(yValue.toString());
            csvWriter.append("\n");
        }

        csvWriter.close();
    }

    /**
     * This method is triggered when the user clicks the "Save Image" button.
     * It captures a snapshot of the current state of the user's Pie chart and
     * LineChart.
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