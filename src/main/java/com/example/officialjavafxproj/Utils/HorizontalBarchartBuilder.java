package com.example.officialjavafxproj.Utils;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

public class HorizontalBarchartBuilder extends AbstractBarchartBuilder<Number, String> {
    private BarChart<Number, String> barChart;
    private CategoryAxis yAxis  = new CategoryAxis();
    private NumberAxis  xAxis = new NumberAxis();

    private HorizontalBarchartBuilder(){
        super();
//        xAxis.setTickUnit(1);
//        xAxis.setAutoRanging(false);
        barChart = new BarChart<Number, String>(xAxis, yAxis);
        barChart.setLegendVisible(false);
    }

    public static HorizontalBarchartBuilder builder(){
        return new HorizontalBarchartBuilder();
    }

    public HorizontalBarchartBuilder withXAxis(String xLabel){
        barChart.getXAxis().setLabel(xLabel);
        return this;
    }

    public HorizontalBarchartBuilder withYAxis(String yLabel){
        barChart.getYAxis().setLabel(yLabel);
        return this;
    }

    public HorizontalBarchartBuilder withMaxWidth(double v){
        barChart.setMaxWidth(v);
        return this;
    }

    public HorizontalBarchartBuilder withMaxHeight(double v){
        barChart.setMaxHeight(v);
        return this;
    }

    public HorizontalBarchartBuilder withTitle(String title){
        barChart.setTitle(title);
        return this;
    }

    public HorizontalBarchartBuilder withData(ArrayList<String[]> data){
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        series.setName("Star");
        for(String[] dataElement : data){
            series.getData().add(new XYChart.Data<>(Integer.parseInt(dataElement[1]), dataElement[0]));
        }
        barChart.getData().add(series);
        return this;
    }

    public HorizontalBarchartBuilder withBarColorAll(String color, int barsNum){
        for (int i = 0; i < barsNum; i++) {
            for(Node n:barChart.lookupAll(".default-color" + i + ".chart-bar")) {
                n.setStyle("-fx-bar-fill: #FFB84C;");

            }
        }

//        Legend legend = (Legend) barChart.lookup(".chart-legend");
//        Legend.LegendItem legendItem = new Legend.LegendItem("Stars", new Rectangle(25,25, Color.rgb(255,184,76)));
//        legend.getItems().setAll(legendItem);
        return this;

    }


    public BarChart<Number, String> build(){
        return barChart;
    }

}
