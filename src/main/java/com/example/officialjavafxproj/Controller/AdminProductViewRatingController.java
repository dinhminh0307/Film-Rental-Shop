package com.example.officialjavafxproj.Controller;

import FileLocation.FileLocation;
import Model.Product.Product;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminProductViewRatingController implements Initializable,UIController {
    @FXML
    private AnchorPane navbarPane;

    @FXML
    private ImageView productDetailImage;

    @FXML
    private Label productDetailTitleDisplay;
    @FXML
    private VBox reviewBoxDisplay;

    @FXML
    private AnchorPane ratingChartDisplay;
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
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavBarComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void loadPageContent(){
        Product currentProduct = ProductService.builder().getTargetProduct();
        String imageDir = FileLocation.getImageDir() + currentProduct.getImageLocation();
        try {
            Image productImage = new Image(new FileInputStream(imageDir), 350, 280, false, false);
            productDetailImage.setImage(productImage);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        productDetailTitleDisplay.setText(currentProduct.getTitle());
    }
    public void addReviewBox(){
        try {
            reviewBoxDisplay.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminReviewBoxComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addRatingChart(){
        try {
            ratingChartDisplay.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminReviewChartComponent.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void backButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent,"../Pages/adminProductDetail.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        loadPageContent();
        addRatingChart();
        addReviewBox();
    }
}
