package com.example.officialjavafxproj.Controller;

import FileLocation.FileLocation;
import Model.Account.GuestAccount;
import Model.Account.RegularAccount;
import Model.Account.VIPAccount;
import Model.User.Customer;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileControllers implements Initializable,UIController {

    @FXML
    private ImageView profileImage;

    @FXML
    private AnchorPane navbarPane;
    @FXML
    private Label customerIdDisplay;

    @FXML
    private Label fullNameDisplay;

    @FXML
    private Label addressDisplay;

    @FXML
    private Label phoneNumDisplay;

    @FXML
    private Label balanceDisplay;

    @FXML
    private Label statusDisplay;

    @FXML
    private Label accountIdDisplay;

    @FXML
    private Label accountTypeDisplay;

    @FXML
    private Label accountPointsDisplay;

    @FXML
    private Label noReturnedItemsDisplay;

    @FXML
    private Label noFreeToBorrowDisplay;

    @FXML
    private Label noMaximumItemsDisplay;

    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageContent(){
        UserServices userServices = UserServices.builder();

        if(userServices.getCurrentUser() instanceof Customer){
            Customer currentCustomer = (Customer) userServices.getCurrentUser();
            Image currentUserProfileImg = null;
            String profileImgUrl = FileLocation.getImageDir() + currentCustomer.getImageLocation();
            try {
                currentUserProfileImg = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
            profileImage.setImage(currentUserProfileImg);
            customerIdDisplay.setText(currentCustomer.getUserId());
            fullNameDisplay.setText(currentCustomer.getFullName());
            addressDisplay.setText(currentCustomer.getAddress());
            phoneNumDisplay.setText(currentCustomer.getPhoneNum());
            balanceDisplay.setText(String.valueOf(currentCustomer.getBalance()));
            if(currentCustomer.getRentalList().size() > 0){
                currentCustomer.getAccount().setCurrentlyBorrowed(true);
            }else{
                currentCustomer.getAccount().setCurrentlyBorrowed(false);
            }
            statusDisplay.setText(String.valueOf(currentCustomer.getAccount().getIsCurrentlyBorrowed()));
            accountIdDisplay.setText(currentCustomer.getAccount().getAccountId());
            noReturnedItemsDisplay.setText(String.valueOf(currentCustomer.getAccount().getNumReturnedItems()));
            noMaximumItemsDisplay.setText(String.valueOf(currentCustomer.getAccount().getRentalThreshold()));
            accountTypeDisplay.setText(currentCustomer.getAccount().getAccountType());
            if(currentCustomer.getAccount() instanceof GuestAccount ){
                accountPointsDisplay.setText("None");
                noFreeToBorrowDisplay.setText("Not Now");
            }else if(currentCustomer.getAccount() instanceof RegularAccount){
                accountPointsDisplay.setText("None");
                noFreeToBorrowDisplay.setText("Not Now");
            }else{
                VIPAccount currentUserAccount = (VIPAccount) currentCustomer.getAccount();
                accountPointsDisplay.setText(String.valueOf(currentUserAccount.getPoints()));
                noFreeToBorrowDisplay.setText(currentUserAccount.getPoints() > 100 ? "1" : "Points must > 100");
            }
        }
    }

    public void onEditProfile(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/editUserProfile.fxml");
    }

    public void onBackToShop(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/homepage.fxml");
    }

    public void addNavigationBar(){
        navbarPane.getChildren().clear();
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/navbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        loadPageContent();
        addFooterBar();
    }
}
