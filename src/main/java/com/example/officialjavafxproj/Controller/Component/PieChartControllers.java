package com.example.officialjavafxproj.Controller.Component;

import DataAccess.DataAccess;
import Service.AccountService;
import Service.ProductService;
import com.example.officialjavafxproj.Utils.BarchartBuilder;
import com.example.officialjavafxproj.Utils.ChartDataController;
import com.example.officialjavafxproj.Utils.PieChartBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class PieChartControllers implements Initializable {
    @FXML
    private AnchorPane pieChartDisplay;
    public void setUpBarChart(){
        PieChart pieChart = PieChartBuilder.builder()
                .withTitle("Accounts Distribution")
                .withAccountData(ChartDataController.getChartAccountData(AccountService.getAllAccounts()))
                .withMaxWidth(500)
                .withMaxHeight(300)
                .withValueDisplay(true)
                .build();
        pieChartDisplay.getChildren().add(pieChart);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpBarChart();
    }
}
