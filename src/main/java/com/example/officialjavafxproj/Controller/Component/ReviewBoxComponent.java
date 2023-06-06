package com.example.officialjavafxproj.Controller.Component;

import Model.Form.Feedback;
import Model.User.User;
import Service.FeedbackService;
import Service.ProductService;
import Service.UserServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ReviewBoxComponent implements Initializable {

    @FXML
    private VBox reviewDisplay;

    @FXML
    private Label commentsNumDisplay;

    public void loadAllReviews(){
        int total = 0;
        reviewDisplay.setSpacing(10);
        try {
            for(Map.Entry<String, User> user : UserServices.builder().getAll().entrySet()){
                for(Feedback feedback : user.getValue().getReviews()){
                    if(feedback.getProductId().equals(ProductService.builder().getTargetProduct().getId())){
                        total++;
                        FXMLLoader reviewsLoader = new FXMLLoader();
                        reviewsLoader.setLocation(getClass().getResource("../../Component/reviewComponent.fxml"));
                        VBox review = reviewsLoader.load();
                        ReviewComponent reviewComponent = reviewsLoader.getController();
                        reviewComponent.loadReviewElement(feedback);
                        reviewDisplay.getChildren().add(review);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        commentsNumDisplay.setText(String.valueOf(total));

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAllReviews();
    }
}
