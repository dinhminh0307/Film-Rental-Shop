package com.example.officialjavafxproj.Controller.Component;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Model.User.Customer;
import Model.User.User;
import Service.AdminService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.IOException;

public class AdminUserControllers {
    @FXML
    private ImageView imageView;
    @FXML
    private Label userNameDisplay;
    @FXML
    private Label userIdDisplay;
    @FXML
    private Label userPhoneDisplay;
    @FXML
    private Label userAddressDisplay;
    private String userID;


    public void loadDisplayUser(User user) {
        String imageDir = FileLocation.getImageDir() + user.getImageLocation();
        try {
            Image userImage = new Image(new FileInputStream(imageDir), 200, 200, false, false);
            imageView.setImage(userImage);

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        userNameDisplay.setText(user.getFullName());
        userIdDisplay.setText(user.getUserId());
        userPhoneDisplay.setText(user.getPhoneNum());
        userAddressDisplay.setText(user.getAccount().getAccountType());
        userID = user.getUserId();
    }

    public void onViewUserProfileButton(MouseEvent mouseEvent) throws IOException {
        AdminService adminService = AdminService.builder();
        User selectedUser = adminService.getOne(userID);
        AdminService.setSelectedUser(selectedUser);
        SceneController.switchScene(mouseEvent, "../Pages/adminViewProfile.fxml");
    }
}
