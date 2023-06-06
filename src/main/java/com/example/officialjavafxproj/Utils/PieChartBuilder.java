package com.example.officialjavafxproj.Utils;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class PieChartBuilder {
    private PieChart pieChart;
    private PieChartBuilder(){
        pieChart = new PieChart();
    }

    public static PieChartBuilder builder(){
        return new PieChartBuilder();
    }

    public PieChartBuilder withTitle(String title){
        pieChart.setTitle(title);
        return this;
    }

    public PieChartBuilder withAccountData(ArrayList<String[]> data){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for(String[] dataElement : data){
            pieChartData.add( new PieChart.Data(dataElement[0], Double.parseDouble(dataElement[1])));
        }
        pieChart.setData(pieChartData);
//        System.out.println(pieChartData.get(0));
        return this;
    }

    public PieChartBuilder withValueDisplay(Boolean isDisplayData){
        if(isDisplayData){
            pieChart.getData().forEach(data -> {
                data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty()));
            });
        }
        return this;
    }

    public PieChartBuilder withMaxWidth(double v){
        pieChart.setMaxWidth(v);
        return this;
    }

    public PieChartBuilder withMaxHeight(double v){
        pieChart.setMaxHeight(v);
        return this;
    }



    public PieChart build(){
        return pieChart;
    }

}
