package com.example.officialjavafxproj.Controller.Component;
import FileLocation.FileLocation;
import Model.Product.Product;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.BarchartBuilder;
import com.example.officialjavafxproj.Utils.ChartDataController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BarChartControllers implements Initializable {

    @FXML
    private AnchorPane barChartDisplay;

    private String[] groups;
    private String[] genres;

    public void setGroups(String[] types){
        groups = types;
    }
    public void setGenres(String[] itemGenres){
        genres = itemGenres;
    }

    public void setUpBarChart(){
        BarChart<String, Number> barChart = BarchartBuilder.builder()
//                .withXCategories(FXCollections.observableArrayList(groups))
                .withXAxis("Rental Types")
                .withYAxis("Number")
                .withProductData(ChartDataController.getChartProductData(ProductService.builder().getArrayProducts(), groups), genres)
                .withMaxWidth(400)
                .withMaxHeight(300)
                .withTitle("Details Products Number")
                .build();

        barChartDisplay.getChildren().add(barChart);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setGroups(Product.getRentalTypes());
        setGenres(Product.getGenres());
        setUpBarChart();
    }
}
