package com.example.officialjavafxproj.Controller;

import FileLocation.FileLocation;
import Model.Form.Feedback;
import Model.Product.Product;
import Service.FeedbackService;
import Service.ProductService;
import Service.UserServices;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserRatingControllers implements Initializable, UIController{

    @FXML
    private TextArea commentsTextField;

    @FXML
    private VBox reviewBoxDisplay;

    @FXML
    private AnchorPane navbarPane;

    @FXML
    private Label productDetailGenreDisplay;

    @FXML
    private ImageView productDetailImage;

    @FXML
    private Label productDetailLoanTypeDisplay;


    @FXML
    private Label productDetailStatusDisplay;

    @FXML
    private Label productDetailStockDisplay;

    @FXML
    private Label productDetailTitleDisplay;

    @FXML
    private Label productDetailTypeDisplay;

    @FXML
    private Label productDetailYearDisplay;

    @FXML
    private Label averageStarDisplay;

    @FXML
    private ChoiceBox<Integer> ratingSelection;

    @FXML
    private AnchorPane ratingChartDisplay;

    @FXML
    private Button postReviewButton;

    private final UserServices userServices = UserServices.builder();

    private final ProductService productService = ProductService.builder();


    private Integer[] stars = {1,2,3,4,5};

    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackToDetailButton(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/productDetails.fxml");
    }

    @FXML
    public void onPostButton(ActionEvent event) throws IOException{
        if(ratingSelection.getValue() == null || commentsTextField.getText().trim().equals("")){
            ToastBuilder.builder()
                    .withMode(Notifications.ERROR)
                    .withTitle("Review Message")
                    .withMessage("You must include your ratings and comments ")
                    .show();
        }else{
            Feedback feedback = Feedback.builder()
                    .withCustomerId(userServices.getCurrentUser().getUserId())
                    .withProductId(productService.getTargetProduct().getId())
                    .withRating(ratingSelection.getValue())
                    .withFeedbackContent(commentsTextField.getText())
                    .withReviewDate(LocalDate.now())
                    .build();
            productService.getTargetProduct().addFeedback(feedback);
            userServices.getCurrentUser().addReview(feedback);
            FeedbackService.addFeedbackToDb(feedback);
            SceneController.switchScene(event, "../Pages/userRatingItem.fxml");
            ToastBuilder.builder()
                    .withMode(Notifications.SUCCESS)
                    .withTitle("Review Message")
                    .withMessage("You have posted successfully!")
                    .show();
        }

    }

    public void setUpRatingSelection(){
        ratingSelection.getItems().addAll(stars);
    }
    public void loadPageContent(){
        Product currentProduct = productService.getTargetProduct();
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

    public void addNavigationBar(){
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/navbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPageContent();
        setUpRatingSelection();
        addNavigationBar();
        addReviewBox();
        addRatingChart();
        addFooterBar();
    }
}
