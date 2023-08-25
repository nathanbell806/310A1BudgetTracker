package com.example.budgettracker.controller;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CreateProfileController {
    @FXML
    TextField usernameTextField;

    @FXML
    Label accountLabel;
    ProfileRepository profileRepository;
    CurrentProfile currentProfile;

    ChangeScene changeScene;
    public void initialize() throws IOException {
        profileRepository = new ProfileRepository();
        currentProfile = CurrentProfile.getInstance();
        changeScene = new ChangeScene();
    }

    @FXML
    public void onCreate(){

        Boolean didWork = profileRepository.createProfile(usernameTextField.getText(), currentProfile.getProfileSlot());
        if(Boolean.TRUE.equals(didWork)){
            currentProfile.setCurrentProfile(profileRepository.selectProfile(usernameTextField.getText()));
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

    @FXML
    private void onUserKey( ) {
        accountLabel.setText(usernameTextField.getText());
    }

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        changeScene.changeScene(event, SceneName.SELECT_PROFILE);
    }


}
