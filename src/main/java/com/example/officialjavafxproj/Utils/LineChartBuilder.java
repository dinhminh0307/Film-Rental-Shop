package com.example.officialjavafxproj.Utils;

import Middleware.DateMiddleware;
import javafx.scene.chart.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class LineChartBuilder {
    private LineChart<String, Number> lineChart;
    private CategoryAxis xAxis = new CategoryAxis();
    private NumberAxis yAxis = new NumberAxis();

    private LineChartBuilder(){
        lineChart = new LineChart<String, Number>(xAxis, yAxis);
    }
    public LineChartBuilder withXAxis(String xLabel){
        lineChart.getXAxis().setLabel(xLabel);
        return this;
    }

    public LineChartBuilder withYAxis(String yLabel){
        lineChart.getYAxis().setLabel(yLabel);
        return this;
    }

    public LineChartBuilder withMaxWidth(double v){
        lineChart.setMaxWidth(v);
        return this;
    }

    public LineChartBuilder withMaxHeight(double v){
        lineChart.setMaxHeight(v);
        return this;
    }

    public LineChartBuilder withTitle(String title){
        lineChart.setTitle(title);
        return this;
    }


    public static LineChartBuilder builder(){
        return new LineChartBuilder();
    }

    public LineChartBuilder withData(ArrayList<String[]> data){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(String[] dataElement : data){
            series.setName("Revenue");
            series.getData().add(new XYChart.Data<>(dataElement[0], Double.parseDouble(dataElement[1])));
        }
        lineChart.getData().add(series);
        return this;
    }

    public LineChart<String, Number> build(){
        return lineChart;
    }
}
