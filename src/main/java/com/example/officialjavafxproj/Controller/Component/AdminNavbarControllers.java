package com.example.officialjavafxproj.Controller.Component;

//import Service.OrderDetailService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminNavbarControllers {
    @FXML
    private Label userNameDisplay;

//    private Label noCartItem;
    public void onLogoutButton(ActionEvent event) throws IOException {
        UserServices.builder().setCurrentUser(null);
        SceneController.switchScene(event, "../Pages/login.fxml");
    }


    public void onHomeButton(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/adminViewCustomers.fxml");
    }
    public void onViewProductButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/adminViewProduct.fxml");
    }
    public void onViewUsersButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/adminViewCustomers.fxml");
    }

    public void onViewOrdersButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/adminViewOrders.fxml");
    }

    public void onHomepageButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/homepageAdmin.fxml");
    }
}
