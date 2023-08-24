package com.example.budgettracker.controller;


import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Profile;
import com.example.budgettracker.profiles.ProfileFactory;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    private static final String DESTIONATION_CREATE_PROFILE = "/com/example/budgettracker/create-profile.fxml";
    private static final String DESTIONATION_BUDGET_ENTRY = "/com/example/budgettracker/budget-entry.fxml";
    CurrentProfile currentProfile;
    ProfileFactory profileFactory;

    ChangeScene changeScene;
    private static final String CREATE_ACCOUNT = "Create Account";


    public void initialize() throws IOException {
        currentProfile = CurrentProfile.getInstance();
        profileFactory = new ProfileFactory();
        changeScene = new ChangeScene();
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
    private void onProfileButtonOne(MouseEvent actionEvent) throws IOException {
        if (accountOneLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(1);
            changeScene.changeScene(actionEvent,DESTIONATION_CREATE_PROFILE );
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountOneLabel.getText()));
            changeScene.changeScene(actionEvent,DESTIONATION_BUDGET_ENTRY );
            accountOneLabel.setText("done");
        }
    }
    @FXML
    private void onProfileButtonTwo(MouseEvent actionEvent) throws IOException {
        if (accountTwoLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(2);
            changeScene.changeScene(actionEvent,DESTIONATION_CREATE_PROFILE );
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountTwoLabel.getText()));
            changeScene.changeScene(actionEvent,DESTIONATION_BUDGET_ENTRY );
            accountTwoLabel.setText("done");
        }
    }
    @FXML
    private void onProfileButtonThree(MouseEvent actionEvent) throws IOException {
        if (accountThreeLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(3);
            changeScene.changeScene(actionEvent,DESTIONATION_CREATE_PROFILE );
        } else {
            currentProfile.setCurrentProfile(profileFactory.selectProfile(accountThreeLabel.getText()));
            changeScene.changeScene(actionEvent,DESTIONATION_BUDGET_ENTRY );
            accountThreeLabel.setText("done");
        }
    }
}
