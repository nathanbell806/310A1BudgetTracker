package com.example.budgettracker.controller;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Expense;
import com.example.budgettracker.profiles.Profile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class ChartController {

    private final PieChart pieChart;
    private final LineChart<Number, Number> lineChart;
    private final Tooltip tooltip;

    public ChartController(PieChart pieChart, LineChart<Number, Number> lineChart, Tooltip tooltip) {
        this.pieChart = pieChart;
        this.lineChart = lineChart;
        this.tooltip = tooltip;
    }

    public void initialize() {
        dataGet();
        addEventHandlersCharts();
    }

    private void addEventHandlersCharts() {
        pieChart.setTitle("Budget Breakdown Pie Chart");
        addEventHandlersToPieChart(pieChart);
        addEventHandlersToLineChart(lineChart);
    }

    private void addEventHandlersToPieChart(PieChart pieChart) {
        tooltip.setStyle("-fx-font-size: 20");
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                if (!tooltip.isShowing()) {
                    // Set the tooltip text to display the value when the mouse enters the slice
                    double value = data.getPieValue();
                    int roundedValue = (int) Math.floor(value);
                    String tooltipText = "$" + roundedValue;
                    tooltip.setText(tooltipText);
                    tooltip.show(data.getNode(), e.getScreenX() + 10, e.getScreenY() + 10);
                }
            });
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
                // Hide the tooltip when the mouse exits the slice
                tooltip.hide();
            });
        }
    }

    private void addEventHandlersToLineChart(LineChart<Number, Number> lineChart) {
        // Add event handlers to each data series in the LineChart
        for (XYChart.Series<Number, Number> series : lineChart.getData()) {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
                    // Set the tooltip text to display the value when the mouse enters the data point
                    double value = data.getYValue().doubleValue(); // Ensure the value is a double
                    int roundedValue = (int) Math.floor(value);
                    String tooltipText = "$" + roundedValue;
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


    private void dataGet() {
        Profile profile = CurrentProfile.getInstance().getCurrentProfile();
        ObservableList<PieChart.Data> budgetData = retrievePieChartData(profile);
        XYChart.Series<Number, Number> series = retrieveLineChartData(profile);

        pieChart.setData(budgetData);
        lineChart.getData().add(series);
    }

    private ObservableList<PieChart.Data> retrievePieChartData(Profile profile) {
        ObservableList<PieChart.Data> budgetData = FXCollections.observableArrayList();
        double totalBudgetLeft = profile.getBudget();

        for (Expense expense : profile.getExpenses()) {
            budgetData.add(new PieChart.Data(expense.getName(), expense.getCost()));
        }

        double totalExpense = getTotalExpense(profile);
        totalBudgetLeft = totalBudgetLeft - totalExpense;

        if (totalBudgetLeft > 0) {
            budgetData.add(new PieChart.Data("Savings", totalBudgetLeft));
        }

        return budgetData;
    }

    private double getTotalExpense(Profile profile) {
        return profile.getExpenses().stream().mapToDouble(Expense::getCost).sum();
    }

    private XYChart.Series<Number, Number> retrieveLineChartData(Profile profile) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Your Current Spending");

        double totalBudgetLeft = profile.getBudget() - getTotalExpense(profile);

        for (int i = 0; i < 11; i++) {
            series.getData().add(new XYChart.Data<>(i, forecastSavings(profile, i, totalBudgetLeft)));
        }

        return series;
    }

    private double forecastSavings(Profile profile, int i, double totalBudgetLeft) {
        return (totalBudgetLeft) * i * 52;
    }

    public String getTotalBudget() {
        return String.valueOf(CurrentProfile.getInstance().getCurrentProfile().getBudget());
    }
}
