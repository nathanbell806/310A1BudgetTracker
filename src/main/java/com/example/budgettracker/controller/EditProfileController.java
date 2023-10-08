package com.example.budgettracker.controller;

import com.example.budgettracker.ChangeScene;
import com.example.budgettracker.SceneName;
import com.example.budgettracker.profiles.CurrentProfile;
import com.example.budgettracker.profiles.ProfileRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfileController implements Initializable {

    @FXML
    ImageView editPicture;
    @FXML
    TextField editUserName;
    @FXML
    Button editUser;
    @FXML
    Label currentName;
    @FXML
    ImageView profileView;
    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<Number, Number> lineChart;

    boolean edit = false;
    ProfileRepository profileRepository;
    StringBuilder oldName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            profileRepository = new ProfileRepository();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (CurrentProfile.getInstance().getCurrentProfile().getProfilePicture() != null) {
            Image image = new Image("file:" + CurrentProfile.getInstance().getCurrentProfile().getProfilePicture());
            profileView.setImage(image);
        }
        ChartController chartController = new ChartController(pieChart, lineChart, new Tooltip());
        chartController.initialize();
        oldName = new StringBuilder(CurrentProfile.getInstance().getCurrentProfile().getUsername());
        currentName.setText(CurrentProfile.getInstance().getCurrentProfile().getUsername());
    }

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        ChangeScene changeScene = new ChangeScene();
        changeScene.changeScene(event, SceneName.SELECT_PROFILE);
    }

    @FXML
    protected void onEditPicture() {
        // allow user to select a picture from their computer and set it as their profile picture
        // open file explorer
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        Stage stage = (Stage) editPicture.getScene().getWindow();
        String path = fileChooser.showOpenDialog(stage).getPath();
        CurrentProfile.getInstance().getCurrentProfile().setProfilePicture(path);
        profileRepository.updateProfilePicture(CurrentProfile.getInstance().getCurrentProfile().getUsername(), path);
        Image image = new Image("file:" + path);
        profileView.setImage(image);
        System.out.println("Profile picture changed to " + CurrentProfile.getInstance().getCurrentProfile().getProfilePicture());
    }

    @FXML
    protected void onEditUser() {
        if (edit) {
            edit = false;
            editUserName.setText("");
            editUserName.setEditable(false);
            editUserName.setVisible(false);
            editPicture.setVisible(false);
            currentName.setText(CurrentProfile.getInstance().getCurrentProfile().getUsername());
            profileRepository.updateUsername(oldName.toString(), CurrentProfile.getInstance().getCurrentProfile().getUsername());
            editUser.setText("Edit");
        } else {
            edit = true;
            editUserName.setEditable(true);
            editUserName.setVisible(true);
            editPicture.setVisible(true);
            editUser.setText("Save");
        }
    }

    @FXML
    protected void onUserKey() {
        CurrentProfile.getInstance().getCurrentProfile().setUsername(editUserName.getText());
        System.out.println("User name changed to " + CurrentProfile.getInstance().getCurrentProfile().getUsername());
    }

}
