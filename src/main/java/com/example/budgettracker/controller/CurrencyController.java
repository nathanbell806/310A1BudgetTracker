package com.example.budgettracker.controller;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyController {
        private static final String API_KEY = "029bda5c26bfdab737358d97749e4d94";
        private static final String API_URL = "http://api.exchangeratesapi.io/v1/latest?access_key=" + API_KEY;
        private static final String SAVE_PATH = "C:\\Users\\min\\IdeaProjects\\310A1BudgetTracker\\src\\main\\java\\data\\exchange_rates.json";

        public void fetchAndSaveExchangeRates() {
            try {
                // Connect to the API
                URL url = new URL(API_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // Parse the response to a JSON object
                JSONObject jsonResponse = new JSONObject(content.toString());

                // Save the JSON object to the specified file location
                try (FileWriter file = new FileWriter(SAVE_PATH)) {
                    file.write(jsonResponse.toString(4));  // Indented with 4 spaces
                    file.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
