package com.example.officialjavafxproj.Controller.Component;

import FileLocation.FileLocation;
import Model.Order.OrderDetail;
import Service.OrderDetailCartService;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CartComponentControllers {

    private String cartItemId;
    @FXML
    private ImageView productCartImage;

    @FXML
    private Label productCartTitleDisplay;

    @FXML
    private Label productCartPriceDisplay;

    @FXML
    private Label warningMessage;

    @FXML
    private Label quantityDisplay;

    @FXML
    private Button downButton;

    @FXML
    private Button increaseButton;

    @FXML
    private Label productCartLoanDisplay;

    private OrderDetail currentOrderDetail;

    public void loadCartItemData(OrderDetail details){
        currentOrderDetail = details;
        String imageDir = FileLocation.getImageDir() + details.getBoughtItem().getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 200, 170, false, false);
            productCartImage.setImage(productImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        cartItemId = details.getOrderDetailId();
        productCartTitleDisplay.setText(details.getBoughtItem().getTitle());
        productCartLoanDisplay.setText(details.getBoughtItem().getLoanType());
        quantityDisplay.setText(String.valueOf(details.getQuantity()));
        productCartPriceDisplay.setText(String.valueOf(details.getBoughtItem().getRentalFee() * Integer.parseInt(quantityDisplay.getText())));
        setDownButton();
        setWarningLabel(details.getBoughtItem().getNumOfCopies());
        quantityDisplay.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals("1")){
                downButton.setDisable(true);
            }else{
                downButton.setDisable(false);
            }
            if(Integer.parseInt(newValue) > details.getBoughtItem().getNumOfCopies()){
                warningMessage.setText("Out of Stock !!!");
            }else{
                warningMessage.setText("");
            }
        });
    }

    public String deleteSelf(){
        if(ProductService.builder().getOne(currentOrderDetail.getBoughtItem().getId()) == null){
            return "deleted";
        }
        return null;
    }

    public OrderDetail detailNeedDelete(){
        return currentOrderDetail;
    }

    public void onViewItemButton(ActionEvent actionEvent) throws IOException{
        ProductService.builder().setTargetProduct(currentOrderDetail.getBoughtItem());
        SceneController.switchScene(actionEvent, "../Pages/productDetails.fxml");
    }

    public void onDownButton(ActionEvent event) throws IOException{
        OrderDetailCartService orderDetailCartService = OrderDetailCartService.builder();
        OrderDetail currentItem = orderDetailCartService.getOne(cartItemId);
        currentItem.setQuantity(currentItem.getQuantity() - 1);
        quantityDisplay.setText(String.valueOf(currentItem.getQuantity()));
        productCartPriceDisplay.setText(String.valueOf(currentItem.getBoughtItem().getRentalFee() * Integer.parseInt(quantityDisplay.getText())));
    }
    public void onUpButton(ActionEvent event) throws IOException{
        downButton.setDisable(false);
        OrderDetailCartService orderDetailCartService = OrderDetailCartService.builder();
        OrderDetail currentItem = orderDetailCartService.getOne(cartItemId);
        currentItem.setQuantity(currentItem.getQuantity() + 1);
        quantityDisplay.setText(String.valueOf(currentItem.getQuantity()));
        productCartPriceDisplay.setText(String.valueOf(currentItem.getBoughtItem().getRentalFee() * Integer.parseInt(quantityDisplay.getText())));


    }
    public void onDeleteButton(ActionEvent event) throws IOException{
        OrderDetailCartService orderDetailCartService = OrderDetailCartService.builder();
        OrderDetail currentItem = orderDetailCartService.getOne(cartItemId);
        orderDetailCartService.delete(currentItem);
        SceneController.switchScene(event, "../Pages/userCart.fxml");
    }

    public void onUpdateButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/userCart.fxml");
    }

    public void setDownButton(){
        downButton.setDisable(quantityDisplay.getText().equals("1"));
    }

    public void setWarningLabel(int threshold){
        if(Integer.parseInt(quantityDisplay.getText()) > threshold){
            warningMessage.setText("Out of Stock");
        }
    }

}
