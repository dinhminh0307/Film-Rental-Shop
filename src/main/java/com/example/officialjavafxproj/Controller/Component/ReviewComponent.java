package com.example.officialjavafxproj.Controller.Component;

import Model.Form.Feedback;
import Service.ProductService;
import Service.UserServices;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReviewComponent {

    @FXML
    private Label ratingDisplay;

    @FXML
    private Label reviewDisplay;

    @FXML
    private Label usernameDisplay;

    @FXML
    private Label reviewDateDisplay;

    public void loadReviewElement(Feedback feedback){
        ratingDisplay.setText(String.valueOf(feedback.getRating()));
        reviewDisplay.setText(feedback.getFeedBackContent());
        usernameDisplay.setText(UserServices.builder().getOne(feedback.getCustomerId()).getUserName());
        reviewDateDisplay.setText(feedback.getReviewDate().toString());
    }

}
