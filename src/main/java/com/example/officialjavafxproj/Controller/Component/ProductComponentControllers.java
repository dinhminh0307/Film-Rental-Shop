package com.example.officialjavafxproj.Controller.Component;

import FileLocation.FileLocation;
import Model.Product.Product;
import Service.FeedbackService;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ProductComponentControllers {

    @FXML
    private ImageView productImageView;

    @FXML
    private Label averageRatingDisplay;
    @FXML
    private Label productTitleDisplay;
    @FXML
    private Label productPriceDisplay;

    @FXML
    private Label productStatusDisplay;

    @FXML
    private Label productLoanDisplay;


    private String productId;

    public void loadProductItemData(Product product){
        double rating = FeedbackService.getAverageRatings(product.getId());
        String imageDir = FileLocation.getImageDir() + product.getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 202, 208, false, false);
            productImageView.setImage(productImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        productTitleDisplay.setText(product.getTitle());
        productPriceDisplay.setText(String.valueOf(product.getRentalFee()));
        productLoanDisplay.setText(product.getLoanType());
        productStatusDisplay.setText(product.getStatus());
        averageRatingDisplay.setText(!Double.isNaN(rating) ? String.format("%.1f", rating) : "0");
        productId = product.getId();

    }

    public void onProductClicked(MouseEvent mouseEvent) throws IOException {
        ProductService productService = ProductService.builder();
        Product currentProduct = productService.getOne(productId);
        productService.setTargetProduct(currentProduct);
        SceneController.switchScene(mouseEvent, "../Pages/productDetails.fxml");
    }
}
