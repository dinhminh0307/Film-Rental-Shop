package com.example.officialjavafxproj.Controller.Component;

import FileLocation.FileLocation;
import Model.Product.Product;
import Service.FeedbackService;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Double.NaN;

public class TopProductComponentControllers {

    @FXML
    private AnchorPane topProductPane;
    @FXML
    private Label productTitleDisplay;

    @FXML
    private Label averageRatingDisplay;
    @FXML
    private Label productPriceDisplay;

    @FXML
    private Label productStatusDisplay;

    @FXML
    private Label productLoanDisplay;
    @FXML
    private ImageView productViewDisplay;

    private String productId;


    public void loadTopProductData(Product product){
        double rating = FeedbackService.getAverageRatings(product.getId());
        String imageDir = FileLocation.getImageDir() + product.getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 140, 140, false, false);
            productViewDisplay.setImage(productImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        productTitleDisplay.setText(product.getTitle());
        productPriceDisplay.setText(String.valueOf(product.getRentalFee()));
        productStatusDisplay.setText(product.getStatus());
        productLoanDisplay.setText(product.getLoanType());
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
