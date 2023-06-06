package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Product.Product;
import Model.User.User;
import Service.OrderDetailCartService;
import Service.ProductService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.AlertBuilder;
import com.example.officialjavafxproj.Utils.AlertButtonController;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminProductDetailController implements Initializable, UIController {
    @FXML
    private AnchorPane navbarPane;

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
    private Label productDetailRentalFee;
    @FXML
    private AnchorPane footerPane;

    public void addFooterBar() {
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNavigationBar() {
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavBarComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadPageContent() {
        Product currentProduct = ProductService.builder().getTargetProduct();
        String imageDir = FileLocation.getImageDir() + currentProduct.getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 350, 280, false, false);
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
        productDetailRentalFee.setText(String.valueOf(currentProduct.getRentalFee()));
    }

    public void editProduct(ActionEvent actionEvent) throws IOException {
        Product currentProduct = ProductService.builder().getTargetProduct();
        ProductService.builder().setTargetProduct(currentProduct);
        SceneController.switchScene(actionEvent, "../Pages/adminEditProduct.fxml");
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
        Alert deleteAlert = AlertBuilder.builder()
                .withType(Alert.AlertType.WARNING)
                .withBodyText("Do you want to delete this product?")
                .withHeaderText("Delete warning")
                .withButtonList(AlertButtonController.getDeleteButtonTypes())
                .build();
        ObservableList<ButtonType> buttonTypes = deleteAlert.getButtonTypes();
        Optional<ButtonType> choice = deleteAlert.showAndWait();
        Product currentProduct = ProductService.builder().getTargetProduct();
        if (choice.get() == buttonTypes.get(0)) {
            deleteAlert.close();
        } else {
            if(currentProduct.getIsBeingBorrowed()){
                ToastBuilder.builder()
                        .withTitle("Currently Borrowed")
                        .withMessage("You cannot delete a being borrowed product")
                        .withMode(Notifications.ERROR)
                        .show();
            }else{
                String deletedId = currentProduct.getId();
                ProductService.builder().delete(currentProduct);
                SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
                ToastBuilder.builder()
                        .withTitle("Delete Successfully")
                        .withMessage("The product has been deleted successfully")
                        .withMode(Notifications.SUCCESS)
                        .show();
            }
        }
    }

    public void back(ActionEvent actionEvent) throws IOException {
        SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
    }

    public void viewRating(ActionEvent actionEvent) throws IOException {
        Product currentProduct = ProductService.builder().getTargetProduct();
        ProductService.builder().setTargetProduct(currentProduct);
        SceneController.switchScene(actionEvent, "../Pages/adminProductViewRating.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        loadPageContent();
        addNavigationBar();
    }
}
