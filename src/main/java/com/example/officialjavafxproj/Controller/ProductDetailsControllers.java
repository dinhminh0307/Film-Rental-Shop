package com.example.officialjavafxproj.Controller;

import FileLocation.FileLocation;
import Model.Order.OrderDetail;
import Model.Product.Product;
import Service.*;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ProductDetailsControllers implements Initializable,UIController {
    @FXML
    private AnchorPane navbarPane;

    @FXML
    private VBox reviewBoxDisplay;

    @FXML
    private AnchorPane ratingChartDisplay;

    @FXML
    private ImageView productDetailImage;

    @FXML
    private Label productDetailTitleDisplay;

    @FXML
    private Label productDetailYearDisplay;

    @FXML
    private Label productDetailStatusDisplay;

    @FXML
    private Label productDetailTypeDisplay;

    @FXML
    private Label productDetailGenreDisplay;

    @FXML
    private Label productDetailLoanTypeDisplay;

    @FXML
    private Label productDetailStockDisplay;

    @FXML
    private Label productDetailQuantityDisplay;

    @FXML
    private Button decreaseButton;

    @FXML
    private Button increaseButton;

    @FXML
    private Button addToCartButton;


    @FXML
    private Label averageStarDisplay;

    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNavigationBar(){
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/navbarComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPageContent(){
        Product currentProduct = ProductService.builder().getTargetProduct();
        String imageDir = FileLocation.getImageDir() + currentProduct.getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 350, 200, false, false);
            productDetailImage.setImage(productImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        productDetailTitleDisplay.setText(currentProduct.getTitle());
        productDetailYearDisplay.setText(currentProduct.getPublishedYear());
        productDetailStatusDisplay.setText(currentProduct.getStatus());
        productDetailTypeDisplay.setText(currentProduct.getRentalType());
        productDetailGenreDisplay.setText(currentProduct.getGenre());
        productDetailLoanTypeDisplay.setText(currentProduct.getLoanType());
        productDetailStockDisplay.setText(String.valueOf(currentProduct.getNumOfCopies()));
        averageStarDisplay.setText(String.format("%.1f", FeedbackService.getAverageRatings(ProductService.builder().getTargetProduct().getId())));
        productDetailQuantityDisplay.setText("2");
        productDetailQuantityDisplay.textProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue.equals(productDetailStockDisplay.getText())){
                increaseButton.setDisable(true);
            }else{
                increaseButton.setDisable(false);
            }
            if(newValue.equals("1")){
                decreaseButton.setDisable(true);
            }else{
                decreaseButton.setDisable(false);
            }
        });
    }

    public void addReviewBox(){
        try {
            reviewBoxDisplay.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/reviewBoxComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addRatingChart(){
        try {
            ratingChartDisplay.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/starChartComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onAddToCartButton(ActionEvent event) throws IOException{

        OrderDetailCartService orderDetailCartService = OrderDetailCartService.builder();
        UserCartServices userCartServices = UserCartServices.builder();
        Product currentProduct = ProductService.builder().getTargetProduct();
        boolean isExisted = false;

        for(Map.Entry<String, OrderDetail> detail : orderDetailCartService.getAll().entrySet()){
            if(detail.getValue().getBoughtItem().getId().equals(currentProduct.getId())){
                detail.getValue().setQuantity(detail.getValue().getQuantity() + Integer.parseInt(productDetailQuantityDisplay.getText()));
                ToastBuilder.builder()
                        .withTitle("Cart Message")
                        .withMessage("Added To Cart Successfully")
                        .withMode(Notifications.SUCCESS)
                        .show();
                isExisted = true;
                break;
            }
        }
        if(!isExisted){
            OrderDetail detail = new OrderDetail(OrderDetailCartService.builder().idCreation(), "NaN", userCartServices.getOne("dummy").getCartId(), currentProduct, Integer.parseInt(productDetailQuantityDisplay.getText()));
            orderDetailCartService.add(detail);
            orderDetailCartService.addToGlobal(detail);
            ToastBuilder.builder()
                    .withTitle("Cart Message")
                    .withMessage("Added To Cart Successfully")
                    .withMode(Notifications.SUCCESS)
                    .show();
        }
        SceneController.switchScene(event, "../Pages/homepage.fxml");

    }

    public void onRateItemButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/userRatingItem.fxml");
    }

    public void onCheckCartButton(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/userCart.fxml");
    }

    public void setIncreaseButton(){
        increaseButton.setDisable(Integer.parseInt(productDetailQuantityDisplay.getText()) == Integer.parseInt(productDetailStockDisplay.getText()));
    }
    public void onIncreaseButton(ActionEvent event){
        productDetailQuantityDisplay.setText(String.valueOf(Integer.parseInt(productDetailQuantityDisplay.getText()) + 1));
//        decreaseButton.setDisable(false);
    }


    public void onDecreaseButton(ActionEvent event) {
        productDetailQuantityDisplay.setText(String.valueOf(Integer.parseInt(productDetailQuantityDisplay.getText()) - 1));
    }

    public void setAddToCartButton() {
        addToCartButton.setDisable(productDetailStatusDisplay.getText().equals("BORROWED"));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        loadPageContent();
        setIncreaseButton();
        setAddToCartButton();
        addRatingChart();
        addReviewBox();
        addFooterBar();
    }
}
