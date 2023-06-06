package com.example.officialjavafxproj.Controller;


import DataAccess.DataAccess;
import Model.User.Customer;
import Model.User.User;
import Service.AdminService;

import com.example.officialjavafxproj.Controller.Component.AdminUserControllers;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.SearchController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminViewUserControllers implements Initializable,UIController {
    @FXML
    private AnchorPane navbarPane;
    @FXML
    private AnchorPane footerPane;

    @FXML
    private ChoiceBox<String> accountType;
    @FXML
    private TextField searchUser;
    @FXML
    private Button search;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button deleteSearch;
    @FXML
    private RadioButton increasingOrder;
    @FXML
    private RadioButton decreasingOrder;
    @FXML
    private RadioButton sortByName;
    @FXML
    private RadioButton sortByStatus;

    private HashMap<String, User> filteredUser;

    private String choice;
    private final String[] userType = {"VIP Account", "Regular Account", "Guest Account", "All"};

    public void addFooterBar() {
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDisableSearch(){
        increasingOrder.setDisable(true);
        decreasingOrder.setDisable(true);
        sortByName.setDisable(true);
        sortByStatus.setDisable(true);
        searchUser.setDisable(true);
        search.setDisable(true);

    }
    public void setDisableButton(MouseEvent mouseEvent) throws IOException{
        if(accountType.getValue() == null){
            increasingOrder.setDisable(true);
            decreasingOrder.setDisable(true);
            sortByName.setDisable(true);
            sortByStatus.setDisable(true);
            searchUser.setDisable(true);
            search.setDisable(true);
        }
        else {
            increasingOrder.setDisable(false);
            decreasingOrder.setDisable(false);
            sortByName.setDisable(false);
            sortByStatus.setDisable(false);
            searchUser.setDisable(false);
            search.setDisable(false);
        }
    }


    public void addNavigationBar(){
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addAccountType() {
        accountType.getItems().addAll(userType);
    }
    public void addUserToGridView() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;

            for (Map.Entry<String, User> user : AdminService.builder().getAll().entrySet()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../Component/adminViewUserComponent.fxml"));
                    HBox userItem = fxmlLoader.load();
                    AdminUserControllers adminUserController = fxmlLoader.getController();
                    if(user.getValue().getUserId().equals("ADMIN")){
                        continue;
                    }
                    adminUserController.loadDisplayUser(user.getValue());
                    gridPane.setHgap(20);
                    gridPane.setVgap(10);
                    if(column == 0) {
                        gridPane.add(userItem, column++, row);
                    } else {
                        gridPane.add(userItem, column--, row++);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }
    public void searchDisplayUser() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        if(AdminService.builder().getSortedCustomer().isEmpty()){
            Label temp = new Label();
            temp.setText("No Users matched your requirement");
            gridPane.getChildren().add(temp);
        }
        for (Map.Entry<String, User> user : AdminService.builder().getSortedCustomer().entrySet()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Component/adminViewUserComponent.fxml"));
                HBox userItem = fxmlLoader.load();
                AdminUserControllers adminUserController = fxmlLoader.getController();
                if (user.getValue().getUserId().equals("ADMIN")) {
                    continue;
                }
                adminUserController.loadDisplayUser(user.getValue());
                gridPane.setHgap(20);
                gridPane.setVgap(10);
                if (column == 0) {
                    gridPane.add(userItem, column++, row);
                } else {
                    gridPane.add(userItem, column--, row++);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void loadPageContent() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        if (SearchController.getTempUserContainer().isEmpty()) {
            Label temp = new Label();
            temp.setText("No Users matched your requirement");
            gridPane.getChildren().add(temp);
        }
        for (Map.Entry<String, User> user : SearchController.getTempUserContainer().entrySet()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Component/adminViewUserComponent.fxml"));
                HBox userItem = fxmlLoader.load();
                AdminUserControllers adminUserController = fxmlLoader.getController();
                if (user.getValue().getUserId().equals("ADMIN")) {
                    continue;
                }
                adminUserController.loadDisplayUser(user.getValue());
                gridPane.setHgap(20);
                gridPane.setVgap(10);
                if (column == 0) {
                    gridPane.add(userItem, column++, row);
                } else {
                    gridPane.add(userItem, column--, row++);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void onSearchUserButton(ActionEvent event) {
        gridPane.getChildren().clear();
        choice =  accountType.getValue();
        ArrayList<RadioButton> sortOptions = new ArrayList<>(Arrays.asList(increasingOrder, decreasingOrder, sortByName,sortByStatus));
        AdminService.builder().searchByChoice(choice,sortOptions);
        searchDisplayUser();
        String searchField = searchUser.getText();
        if(!searchField.trim().isEmpty()){
            SearchController.searchByUserIdentify(searchField,AdminService.builder().getSortedCustomer());
            loadPageContent();
        }
    }

    public void setToggle() {
        ToggleGroup toggleGroup = new ToggleGroup();
        increasingOrder.setToggleGroup(toggleGroup);
        decreasingOrder.setToggleGroup(toggleGroup);
        sortByName.setToggleGroup(toggleGroup);
        sortByStatus.setToggleGroup(toggleGroup);
    }

    public void onDeleteSearchButton(ActionEvent event) {
        gridPane.getChildren().clear();
        accountType.getSelectionModel().clearSelection();
        increasingOrder.setSelected(false);
        decreasingOrder.setSelected(false);
        sortByName.setSelected(false);
        sortByStatus.setSelected(false);
        searchUser.clear();
        setDisableSearch();
        AdminService.builder().getSortedCustomer().clear();
        addUserToGridView();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDisableSearch();
        addNavigationBar();
        addUserToGridView();
        addAccountType();
        addFooterBar();
        setToggle();
    }
}