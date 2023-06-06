package com.example.officialjavafxproj;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label welcomeText;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button exitButton;

    @FXML
    private ImageView backgroundImg;

    @FXML
    public void onLoginButton(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/login.fxml");
    }

    public void onRegisterButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/register.fxml");
    }

    public void onExitButton(){
        DataAccess.transferAllData();
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    public void loadBackgroundImg(){
        try {
            Image img = new Image(new FileInputStream(FileLocation.getImageDir() + "/Public/headerIcon.jpg"), 600, 400, false, false);
            backgroundImg.setImage(img);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBackgroundImg();
    }
}