package com.example.budgettracker.controller;


import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.Profile;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
    ImageView plusLabelOne;
    @FXML
    ImageView plusLabelTwo;
    @FXML
    ImageView plusLabelThree;
    @FXML
    ImageView profileViewOne;
    @FXML
    ImageView profileViewTwo;
    @FXML
    ImageView profileViewThree;

    CurrentProfile currentProfile;
    ProfileRepository profileRepository;

    ChangeScene changeScene;
    private static final String CREATE_ACCOUNT = "Add User";

    /**
     * set profile to either create profile or select profile depending on whether account already created or not
     */
    public void initialize() throws IOException {
        currentProfile = CurrentProfile.getInstance();
        profileRepository = new ProfileRepository();
        changeScene = new ChangeScene();
        List<Profile> profiles = profileRepository.getAllProfiles();
        if (profiles.get(0).getUsername().equals("")) {
            accountOneLabel.setText(CREATE_ACCOUNT);
            plusLabelOne.setVisible(true);

        } else {
            accountOneLabel.setText(profiles.get(0).getUsername());
            plusLabelOne.setVisible(false);
            profileViewOne.setVisible(true);
        }
        if (profiles.get(1).getUsername().equals("")) {
            accountTwoLabel.setText(CREATE_ACCOUNT);
            plusLabelTwo.setVisible(true);
        } else {
            accountTwoLabel.setText(profiles.get(1).getUsername());
            plusLabelTwo.setVisible(false);
            profileViewTwo.setVisible(true);
            if (profiles.get(1).getProfilePicture() != null) {
                Image image = new Image("file:" + profiles.get(1).getProfilePicture());
                profileViewTwo.setImage(image);
            }
        }
        if (profiles.get(2).getUsername().equals("")) {
            accountThreeLabel.setText(CREATE_ACCOUNT);
            plusLabelThree.setVisible(true);
        } else {
            accountThreeLabel.setText(profiles.get(2).getUsername());
            plusLabelThree.setVisible(false);
            profileViewThree.setVisible(true);
            if (profiles.get(2).getProfilePicture() != null) {
                Image image = new Image("file:" + profiles.get(2).getProfilePicture());
                profileViewThree.setImage(image);
            }
        }

    }

    @FXML
    private void onProfileButtonOne(MouseEvent actionEvent) throws IOException {
        if (accountOneLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(1);
            changeScene.changeScene(actionEvent, SceneName.CREATE_PROFILE);
        } else {
            currentProfile.setCurrentProfile(profileRepository.selectProfile(accountOneLabel.getText()));
            changeScene.changeScene(actionEvent, SceneName.BUDGET_ENTRY);
            accountOneLabel.setText("done");
        }
    }

    @FXML
    private void onProfileButtonTwo(MouseEvent actionEvent) throws IOException {
        if (accountTwoLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(2);
            changeScene.changeScene(actionEvent, SceneName.CREATE_PROFILE);
        } else {
            currentProfile.setCurrentProfile(profileRepository.selectProfile(accountTwoLabel.getText()));
            changeScene.changeScene(actionEvent, SceneName.BUDGET_ENTRY);
            accountTwoLabel.setText("done");
        }
    }

    @FXML
    private void onProfileButtonThree(MouseEvent actionEvent) throws IOException {
        if (accountThreeLabel.getText().equals(CREATE_ACCOUNT)) {
            currentProfile.setProfileSlot(3);
            changeScene.changeScene(actionEvent, SceneName.CREATE_PROFILE);
        } else {
            currentProfile.setCurrentProfile(profileRepository.selectProfile(accountThreeLabel.getText()));
            changeScene.changeScene(actionEvent, SceneName.BUDGET_ENTRY);
            accountThreeLabel.setText("done");
        }
    }
}
