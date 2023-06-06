
package com.example.officialjavafxproj.Utils;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private static Parent root;
    private static Scene scene;
    private static Stage stage;

    private static double xOffset;
    private static double yOffset;

    private static void draggable(Parent root, Stage stage){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setX(mouseEvent.getScreenX() - xOffset);
                stage.setY(mouseEvent.getScreenY() - yOffset);
            }
        });
    }

    public static void switchScene(Event event, String pathToView) throws IOException {
        root = FXMLLoader.load(SceneController.class.getResource(pathToView));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        draggable(root, stage);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static Parent getComponentScene(Parent container, String pathToComponent) throws IOException {
        FXMLLoader componentLoader = new FXMLLoader();
        componentLoader.setLocation(SceneController.class.getResource(pathToComponent));
        container = componentLoader.load();
        return container;
    }

    public static void setCurrentScene(Stage stage, String pathToView) throws IOException{
        root = FXMLLoader.load(SceneController.class.getResource(pathToView));
        draggable(root, stage);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}

