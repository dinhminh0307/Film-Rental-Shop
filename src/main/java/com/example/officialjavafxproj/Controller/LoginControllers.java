package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginControllers implements Initializable {

    @FXML
    private ImageView loginPanel;
    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button forgotPassButton;

    @FXML
    private Label loginMessage;


    public void loginButtonOnAction(ActionEvent event) throws IOException {
        UserServices service = UserServices.builder();
        if(service.login(usernameTextField.getText(), passwordField.getText())){
            if(UserServices.builder().getCurrentUser().getUserId().equals("ADMIN")){
                SceneController.switchScene(event, "../Pages/homepageAdmin.fxml");
            }
            else {
                SceneController.switchScene(event, "../Pages/userProfile.fxml");
            }

            ToastBuilder.builder()
                    .withTitle("Login Message")
                    .withMessage("Login Successful!")
                    .withMode(Notifications.SUCCESS)
                    .show();
        }else{
            ToastBuilder.builder()
                    .withTitle("Login Message")
                    .withMessage("Sai r dan vl!")
                    .withMode(Notifications.ERROR)
                    .show();
        }
    }

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/register.fxml");
    }

    public void onExitButton(ActionEvent event) throws IOException{
        DataAccess.transferAllData();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void loadPanelImage(){
        try {
            Image image = new Image(new FileInputStream(FileLocation.getImageDir() + "/Public/headerIcon.jpg"), 640, 400, false, false);
            loginPanel.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPanelImage();
    }
}
