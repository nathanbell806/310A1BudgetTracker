package com.example.budgettracker.controller;

import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Profile;
import com.example.budgettracker.profiles.ProfileFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SelectProfileController {

    @FXML
    Label accountOneLabel;
    @FXML
    Label accountTwoLabel;
    @FXML
    Label accountThreeLabel;
    @FXML
    Label plusLabelOne;
    @FXML
    Label plusLabelTwo;
    @FXML
    Label plusLabelThree;
    
    @FXML
    ImageView profileViewOne;
    @FXML
    ImageView profileViewTwo;
    @FXML
    ImageView profileViewThree;


    CurrentProfile currentProfile;
    ProfileFactory profileFactory;

    private static final String CREATE_ACCOUNT = "Create Account";


    public void initialize() throws IOException {
        currentProfile = CurrentProfile.getInstance();
        profileFactory = new ProfileFactory();
        List<Profile> profiles = profileFactory.getAllProfiles();
        if(profiles.get(0).getUsername().equals("")){
            accountOneLabel.setText(CREATE_ACCOUNT);
            plusLabelOne.setVisible(true);
            profileViewOne.setVisible(false);
        }else{
            accountOneLabel.setText(profiles.get(0).getUsername());
            plusLabelOne.setVisible(false);
            profileViewOne.setVisible(true);
        }
        if(profiles.get(1).getUsername().equals("")){
            accountTwoLabel.setText(CREATE_ACCOUNT);
            plusLabelTwo.setVisible(true);
            profileViewTwo.setVisible(false);
        }else{
            accountTwoLabel.setText(profiles.get(1).getUsername());
            plusLabelTwo.setVisible(false);
            profileViewTwo.setVisible(true);
        }
        if(profiles.get(2).getUsername().equals("")){
            accountThreeLabel.setText(CREATE_ACCOUNT);
            plusLabelThree.setVisible(true);
            profileViewThree.setVisible(false);
        }else{
            accountThreeLabel.setText(profiles.get(2).getUsername());
            plusLabelThree.setVisible(false);
            profileViewThree.setVisible(true);
        }

    }
    @FXML
    private void onProfileButtonOne(ActionEvent actionEvent) throws IOException {
        if (accountOneLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(1);
            changeScene(actionEvent);
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountOneLabel.getText()));
            accountOneLabel.setText("done");
        }
    }
    @FXML
    private void onProfileButtonTwo(ActionEvent actionEvent) throws IOException {
        if (accountTwoLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(2);
            changeScene(actionEvent);
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountTwoLabel.getText()));
            accountTwoLabel.setText("done");
        }
    }
    @FXML
    private void onProfileButtonThree(ActionEvent actionEvent) throws IOException {
        if (accountThreeLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(3);
            changeScene(actionEvent);
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountThreeLabel.getText()));
            accountThreeLabel.setText("done");
        }
    }

    public void changeScene(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(("create-profile.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }




}
