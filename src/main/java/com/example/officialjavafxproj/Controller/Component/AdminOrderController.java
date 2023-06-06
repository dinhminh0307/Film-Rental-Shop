package com.example.officialjavafxproj.Controller.Component;

import DataAccess.DataAccess;
import Middleware.DateMiddleware;
import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.User.Customer;
import Service.OrderAdminService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.io.IOException;

public class AdminOrderController {
    @FXML
    private Label orderIdDisplay;
    @FXML
    private Label orderUserDisplay;
    @FXML
    private Label orderDateDisplay;

    @FXML
    private Label orderStatusDisplay;
    @FXML
    private Label orderTotalPriceDisplay;
    private String orderID;

    public void loadDisplayOrder(Order order) {
        int countIsReturned = 0;
        int countIsLate = 0;
        orderIdDisplay.setText(order.getOrderId());
        orderUserDisplay.setText(order.getUserId());
        orderDateDisplay.setText(DateMiddleware.dateAfterFormat(order.getOrderDate()));
        orderTotalPriceDisplay.setText(order.getTotalPrice() + "");
        for(OrderDetail orderDetail : order.getOrders()){
            if(orderDetail.getStatus().equals(OrderDetail.getStatuses()[0])){
                countIsReturned++;
            }else if(orderDetail.getStatus().equals(OrderDetail.getStatuses()[1])){
                countIsLate++;
            }
        }
        if(countIsLate > 0){
            orderStatusDisplay.setText("LATE");
            orderStatusDisplay.setStyle("-fx-text-fill: #E76161");
        }
        else if(countIsReturned == order.getOrders().size()){
            orderStatusDisplay.setText("RETURNED");
            orderStatusDisplay.setStyle("-fx-text-fill: #54B435");
        }
        else{
            orderStatusDisplay.setText("OK");
            orderStatusDisplay.setStyle("-fx-text-fill: #54B435");
        }
        orderID = order.getOrderId();
    }

    public void onViewOrderButton(ActionEvent actionEvent) throws IOException {
        OrderAdminService adminService = OrderAdminService.builder();
        Order order = adminService.getOne(orderID);
        adminService.setSelectedOrder(order);
        SceneController.switchScene(actionEvent, "../Pages/adminViewOrderDetail.fxml");
    }
}
