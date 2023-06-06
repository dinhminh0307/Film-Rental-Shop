package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import Model.Order.Order;
import Model.User.Customer;
import Model.User.User;
import Service.AdminService;
import Service.OrderAdminService;
import com.example.officialjavafxproj.Controller.Component.AdminOrderController;
import com.example.officialjavafxproj.Controller.Component.AdminUserControllers;
import com.example.officialjavafxproj.Utils.SceneController;
import com.sun.javafx.menu.MenuItemBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminViewOrderController implements Initializable,UIController {
    @FXML
    private AnchorPane navbar;
    @FXML
    private TextField searchOrder;
    @FXML
    private GridPane gridPane;
    @FXML
    private RadioButton sortByOrderID;
    @FXML
    private RadioButton sortByOrderDate;
    @FXML
    private RadioButton sortByUserID;
    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNavigationBar() {
        try {
            navbar.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavbarComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadPageContent() {

    }

    private void addAllOrder() {
        addOrder(OrderAdminService.builder().getAll());
    }

    private void addOrder(HashMap<String, Order> orderList) {
        int column = 0;
        int row = 0;
        gridPane.getChildren().clear();
        for(Map.Entry<String, Order> order: orderList.entrySet()) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../Component/adminViewOrderComponent.fxml"));
                HBox userItem = loader.load();
                AdminOrderController adminOrderController = loader.getController();
                adminOrderController.loadDisplayOrder(order.getValue());
//                DataAccess.getAllAdminOrders().add(order.getValue());
                if(column == 2) {
                    column = 0;
                    row++;
                }
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.add(userItem,column++,row);
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void onSearchOrderButton() {
        int column = 0;
        int row = 0;
        gridPane.getChildren().clear();
        for(Map.Entry<String, Order> order: OrderAdminService.builder().getAll().entrySet()) {
            if(searchOrder.getText().equals(order.getKey())) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Component/adminViewOrderComponent.fxml"));
                    HBox userItem = loader.load();
                    AdminOrderController adminOrderController = loader.getController();
                    adminOrderController.loadDisplayOrder(order.getValue());
//                    DataAccess.getAllOrders().add(order.getValue());
                    if (column == 2) {
                        column = 0;
                        row++;
                    }
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.add(userItem, column++, row);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @FXML
    private void onOrderIDSortButton() {
        addOrder(OrderAdminService.builder().getSortedOrderID());
    }

    @FXML
    private void onOrderDateSortButton() {
        addOrder(OrderAdminService.builder().getSortedOrderDate());
    }

    @FXML
    private void onUserIDSortButton() {
        addOrder(OrderAdminService.builder().getSortedUserID());
    }

    private void setToggleGroup() {
        ToggleGroup toggleGroup = new ToggleGroup();
        sortByOrderDate.setToggleGroup(toggleGroup);
        sortByOrderID.setToggleGroup(toggleGroup);
        sortByUserID.setToggleGroup(toggleGroup);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        setToggleGroup();
        addNavigationBar();
        addAllOrder();
    }
}
