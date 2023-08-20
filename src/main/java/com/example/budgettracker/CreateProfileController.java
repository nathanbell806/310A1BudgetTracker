package com.example.budgettracker;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.ProfileFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateProfileController {
    @FXML
    TextField usernameTextField;

    ProfileFactory profileFactory;
    CurrentProfile currentProfile;
    public void initialize() throws IOException {
        profileFactory = new ProfileFactory();
        currentProfile = CurrentProfile.getInstance();
    }

    @FXML
    public void onCreate() throws IOException {

        Boolean didWork = profileFactory.createProfile(usernameTextField.getText(), "black", currentProfile.getProfileSlot());
        if(Boolean.TRUE.equals(didWork)){
            currentProfile.setCurrentProfile(profileFactory.selectProfile(usernameTextField.getText()));
            //take to next page
        }
        else{
            showAlert("sameUsername", "username already created");
        }
    }

    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
