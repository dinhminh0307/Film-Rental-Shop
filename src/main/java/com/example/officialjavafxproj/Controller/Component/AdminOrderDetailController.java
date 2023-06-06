package com.example.officialjavafxproj.Controller.Component;

import FileLocation.FileLocation;
import Middleware.DateMiddleware;
import Model.Order.Order;
import Model.Order.OrderDetail;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.time.LocalDate;

//import java.awt.*;

public class AdminOrderDetailController {
    @FXML
    private Label orderDetailID;
    @FXML
    private Label orderDate;
    @FXML
    private Label orderProductID;
    @FXML
    private Label orderQuantity;
    @FXML
    private ImageView image;
    @FXML
    private Label dueStatus;
    public void loadDisplayOrder(OrderDetail item, Order order) {
        orderDetailID.setText(item.getOrderDetailId());
        orderDate.setText(DateMiddleware.dateAfterFormat(item.getDueDate()));
        orderProductID.setText(item.getBoughtItem().getId());
        orderQuantity.setText(item.getQuantity()+"");
        if(item.getStatus().equals(OrderDetail.getStatuses()[0])){
            dueStatus.setText("RETURNED");
            dueStatus.setStyle("-fx-text-fill: #54B435");
        }else{
            if(item.getDueDate().compareTo(LocalDate.now()) < 0){
                dueStatus.setText("LATE");
                dueStatus.setStyle("-fx-text-fill: #E76161");
            }else{
                dueStatus.setText("OK");
                dueStatus.setStyle("-fx-text-fill: #54B435");
            }

        }

        String imageDir = FileLocation.getImageDir() + item.getBoughtItem().getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 100, 74, false, false);
            image.setImage(productImage);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
