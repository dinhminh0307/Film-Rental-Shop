package com.example.officialjavafxproj.Controller.Component;

import Service.AccountService;
import Service.ProductService;
import Service.RevenueService;
import com.example.officialjavafxproj.Utils.BarchartBuilder;
import com.example.officialjavafxproj.Utils.ChartDataController;
import com.example.officialjavafxproj.Utils.LineChartBuilder;
import com.example.officialjavafxproj.Utils.PieChartBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LineChartControllers implements Initializable {
    @FXML
    private AnchorPane lineChartDisplay;
    public void setUpLineChart(){
        LineChart<String, Number> lineChart = LineChartBuilder.builder()
//                .withXCategories(FXCollections.observableArrayList(groups))
                .withXAxis("Date")
                .withYAxis("Number")
                .withMaxWidth(400)
                .withMaxHeight(300)
                .withData(ChartDataController.getChartRevenueData(RevenueService.builder().getAllRevenue()))
                .withTitle("Shop's Revenue")
                .build();
        lineChartDisplay.getChildren().add(lineChart);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpLineChart();
    }
}
