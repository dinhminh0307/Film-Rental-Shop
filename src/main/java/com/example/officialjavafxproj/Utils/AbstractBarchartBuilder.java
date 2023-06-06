package com.example.officialjavafxproj.Utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;

import java.util.ArrayList;

public abstract class AbstractBarchartBuilder<T, Y> {
    private BarChart<T, Y> barChart;
    private Axis<T> xAxis;
    private Axis<Y> yAxis;

    AbstractBarchartBuilder(){
    }

    public AbstractBarchartBuilder withXAxis(String xLabel){
        barChart.getXAxis().setLabel(xLabel);
        return this;
    }

    public AbstractBarchartBuilder withYAxis(String yLabel){
        barChart.getYAxis().setLabel(yLabel);
        return this;
    }

    public AbstractBarchartBuilder withMaxWidth(double v){
        barChart.setMaxWidth(v);
        return this;
    }

    public AbstractBarchartBuilder withMaxHeight(double v){
        barChart.setMaxHeight(v);
        return this;
    }

    public AbstractBarchartBuilder withTitle(String title){
        barChart.setTitle(title);
        return this;
    }

    public AbstractBarchartBuilder withBarColorAll(String color, int barsNum){
        for (int i = 0; i < barsNum; i++) {
            for(Node n:barChart.lookupAll(".default-color" + i + ".chart-bar")) {
                n.setStyle("-fx-bar-fill: #FFB84C;");

            }
        }
        return this;
    }


    public BarChart<T, Y> build(){
        return barChart;
    }
}
