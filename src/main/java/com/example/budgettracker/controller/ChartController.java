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
        addEventHandlersCharts();
        dataGet();
    }

    private void addEventHandlersCharts() {
        pieChart.setTitle("Budget Breakdown Pie Chart");
        addEventHandlersToPieChart(pieChart);
        addEventHandlersToLineChart(lineChart);
    }

    private void addEventHandlersToPieChart(PieChart pieChart) {
        tooltip.setStyle("-fx-font-size: 20");
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> showTooltip(data.getPieValue()));
            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> hideTooltip());
        }
    }

    private void addEventHandlersToLineChart(LineChart<Number, Number> lineChart) {
        tooltip.setStyle("-fx-font-size: 20");
        for (XYChart.Series<Number, Number> series : lineChart.getData()) {
            for (XYChart.Data<Number, Number> data : series.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, e -> showTooltip(data.getYValue()));
                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, e -> hideTooltip());
            }
        }
    }

    private void showTooltip(Number value) {
        if (!tooltip.isShowing()) {
            tooltip.setText("$" + value);
            tooltip.show(tooltip.getOwnerNode(), tooltip.getAnchorX() + 10, tooltip.getAnchorY() + 10);
        }
    }

    private void hideTooltip() {
        tooltip.hide();
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
            String save = (profile.getSavings() > 0) ? "Extra Savings" : "Savings";
            budgetData.add(new PieChart.Data(save, totalBudgetLeft));
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
