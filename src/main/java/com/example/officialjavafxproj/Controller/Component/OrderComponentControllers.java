package com.example.officialjavafxproj.Controller.Component;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Middleware.DateMiddleware;
import Middleware.OrderMiddleware;
import Model.Order.Order;
import Model.Order.OrderDetail;
import Service.OrderCustomerService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class OrderComponentControllers {
    @FXML
    private Label orderStatus;

    @FXML
    private Label orderDate;

    @FXML
    private Label orderId;

    @FXML
    private Label orderTotalPrice;

    @FXML
    private ImageView orderImage;

    public void loadOrderData(Order order){
        String imageDir = FileLocation.getImageDir() + "Public/orderIcon.png";
        try {
            Image image = new Image(new FileInputStream(imageDir), 200, 145, false, false);
            orderImage.setImage(image);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(OrderDetail detail : order.getOrders()){
            LocalDate temp;
            temp = detail.getDueDate();
            if(temp.compareTo(LocalDate.now()) < 0){
                orderStatus.setText("Warning");
                orderStatus.setStyle("-fx-border-color: #E57C23; -fx-text-fill: #E57C23; -fx-border-radius: 20; -fx-border-width: 2");
            }else{
                orderStatus.setText("Good");
                orderStatus.setStyle("-fx-border-color: #54B435; -fx-text-fill: #54B435; -fx-border-radius: 20; -fx-border-width: 2");
            }
            break;
        }
        orderDate.setText(DateMiddleware.dateAfterFormat(order.getOrderDate()));
        orderId.setText(order.getOrderId());
        orderTotalPrice.setText(String.valueOf(order.getTotalPrice()));
    }

    public void onOrderClicked(MouseEvent mouseEvent) throws IOException {
        OrderCustomerService orderCustomerService = OrderCustomerService.builder();
        Order currentOrder = orderCustomerService.getOne(orderId.getText());
        orderCustomerService.setCurrentOrder(currentOrder);
        SceneController.switchScene(mouseEvent, "../Pages/userOrderId.fxml");
    }


}
