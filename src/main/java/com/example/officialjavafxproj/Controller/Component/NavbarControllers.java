package com.example.officialjavafxproj.Controller.Component;

import Service.OrderDetailCartService;
import Service.ProductService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.SearchController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavbarControllers implements Initializable {

    @FXML
    private Label userNameDisplay;

    @FXML
    private Label noCartItem;

    @FXML
    private TextField searchBar;
    public void onLogoutButton(ActionEvent event) throws IOException {
        UserServices.builder().setCurrentUser(null);
        SceneController.switchScene(event, "../Pages/login.fxml");
    }

    public void onAccountButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/userProfile.fxml");
    }

    public void onHomeButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/homepage.fxml");
    }

    public void onGoToCartButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/userCart.fxml");
    }
    public void onGoToOrders(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/userOrders.fxml");
    }

    public void onSearchButton(ActionEvent event) throws IOException{
        SearchController.searchByIdentify(searchBar.getText(), ProductService.builder().getAll());
        ProductService.builder().setSortedProduct(SearchController.getTempContainer());
        SceneController.switchScene(event, "../Pages/sortPage.fxml");
    }

    public void loadUserName(){
        userNameDisplay.setText(UserServices.builder().getCurrentUser().getUserName());
    }

    public void loadNoCartItem(){
        noCartItem.setText(String.valueOf(OrderDetailCartService.builder().getAll().size()));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserName();
        loadNoCartItem();
    }

}
