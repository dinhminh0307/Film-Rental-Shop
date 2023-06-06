package com.example.officialjavafxproj.Controller;

import com.example.officialjavafxproj.Controller.Component.AdminOverallDataControllers;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomepageAdminControllers implements Initializable,UIController {

    @FXML
    private AnchorPane navbarPane;

    @FXML
    private GridPane chartDisplay;
    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNavigationBar(){
        navbarPane.getChildren().clear();
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavBarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageContent(){
        FXMLLoader overallDataLoader = new FXMLLoader(getClass().getResource("../Component/adminOverallDataComponent.fxml"));
        FXMLLoader barChartLoader = new FXMLLoader(getClass().getResource("../Component/barChartComponent.fxml"));
        FXMLLoader pieChartLoader = new FXMLLoader(getClass().getResource("../Component/pieChartComponent.fxml"));
        FXMLLoader lineChartLoader = new FXMLLoader(getClass().getResource("../Component/lineChartComponent.fxml"));


        try {
            AnchorPane barChart = barChartLoader.load();
            AnchorPane pieChart = pieChartLoader.load();
            AnchorPane lineChart = lineChartLoader.load();
            VBox dataContainer = overallDataLoader.load();
            chartDisplay.add(dataContainer,0,0);
            chartDisplay.setHgap(10);
            chartDisplay.setVgap(10);
            chartDisplay.setAlignment(Pos.CENTER);
            chartDisplay.add(pieChart,1,0);
            chartDisplay.add(barChart,0,1);
            chartDisplay.add(lineChart, 1,1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        loadPageContent();
    }
}
