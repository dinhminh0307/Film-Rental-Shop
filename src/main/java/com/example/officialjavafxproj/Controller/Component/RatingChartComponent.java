package com.example.officialjavafxproj.Controller.Component;

import Service.FeedbackService;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.BarchartBuilder;
import com.example.officialjavafxproj.Utils.ChartDataController;
import com.example.officialjavafxproj.Utils.HorizontalBarchartBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingChartComponent implements Initializable {
    @FXML
    private AnchorPane chartDisplay;

    public void setUpRatingChart(){
//        BarChart<String, Number> ratingChart = BarchartBuilder.builder()
//                .withMaxHeight(250)
//                .withMaxWidth(300)
//                .withRatingData(ChartDataController.getChartRating(FeedbackService.getAllReviews()))
//                .withBarColorAll("#FFB84C", 5)
//                .build();
        BarChart<Number, String> ratingChart = HorizontalBarchartBuilder.builder()
                .withMaxHeight(250)
                .withMaxWidth(300)
                .withData(ChartDataController.getChartRating(FeedbackService.getAllReviews(), ProductService.builder().getTargetProduct().getId()))
                .withBarColorAll("#FFB84C", 5)
                .build();
        chartDisplay.getChildren().add(ratingChart);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpRatingChart();
    }
}
