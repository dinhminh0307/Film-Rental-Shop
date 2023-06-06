package com.example.officialjavafxproj;

import DataAccess.DataAccess;
import com.example.officialjavafxproj.Controller.AdminViewProductController;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        DataAccess.loadAllData();
        stage.initStyle(StageStyle.UNDECORATED);
        SceneController.setCurrentScene(stage,"../hello-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}