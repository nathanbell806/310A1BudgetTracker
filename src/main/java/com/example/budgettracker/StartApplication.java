package com.example.budgettracker;

import com.example.budgettracker.controller.CurrencyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {

    @Override
    public void init() throws Exception {
        // Initialize and fetch exchange rates before the UI starts
        CurrencyController fetcher = new CurrencyController();
        fetcher.fetchAndSaveExchangeRates();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("select-profile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("My Budget Tracker");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
