package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Model.Account.GuestAccount;
import Model.Account.RegularAccount;
import Model.Account.VIPAccount;
import Model.User.Customer;
import Model.User.User;
import Service.AdminService;
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

public class AdminViewProfileControllers implements Initializable,UIController {
    @FXML
    private ImageView profileImage;

    @FXML
    private AnchorPane navbarPane;
    @FXML
    private AnchorPane footerPane;

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
    @Override
    public void addNavigationBar(){
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addFooterBar() {
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadPageContent() {
        AdminService adminService = AdminService.builder();
        User selectedCustomer = DataAccess.getSelectedCustomer();
        Image selectedUserProfileImg = null;
        String profileImgUrl = FileLocation.getImageDir() + selectedCustomer.getImageLocation();
        try {
            selectedUserProfileImg = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        profileImage.setImage(selectedUserProfileImg);
        customerIdDisplay.setText(selectedCustomer.getUserId());
        fullNameDisplay.setText(selectedCustomer.getFullName());
        addressDisplay.setText(selectedCustomer.getAddress());
        phoneNumDisplay.setText(selectedCustomer.getPhoneNum());
        balanceDisplay.setText(String.valueOf(selectedCustomer.getBalance()));
        if(selectedCustomer.getRentalList().size() > 0){
            selectedCustomer.getAccount().setCurrentlyBorrowed(true);
        }else{
            selectedCustomer.getAccount().setCurrentlyBorrowed(false);
        }
        statusDisplay.setText(String.valueOf(selectedCustomer.getAccount().getIsCurrentlyBorrowed()));
        accountIdDisplay.setText(selectedCustomer.getAccount().getAccountId());
        noReturnedItemsDisplay.setText(String.valueOf(selectedCustomer.getAccount().getNumReturnedItems()));
        noMaximumItemsDisplay.setText(String.valueOf(selectedCustomer.getAccount().getRentalThreshold()));
        accountTypeDisplay.setText(selectedCustomer.getAccount().getAccountType());
        if(selectedCustomer.getAccount() instanceof GuestAccount){
            accountPointsDisplay.setText("None");
            noFreeToBorrowDisplay.setText("Not Now");
        }else if(selectedCustomer.getAccount() instanceof RegularAccount){
            accountPointsDisplay.setText("None");
            noFreeToBorrowDisplay.setText("Not Now");
        }else{
            VIPAccount currentUserAccount = (VIPAccount) selectedCustomer.getAccount();
            accountPointsDisplay.setText(String.valueOf(currentUserAccount.getPoints()));
            noFreeToBorrowDisplay.setText(currentUserAccount.getPoints() >= 100 ? "1" : "Points must be over 100 to borrow free");
        }
    }
    public void onUserPageBackButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/adminViewCustomers.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPageContent();
        addNavigationBar();
        addFooterBar();
    }
}
