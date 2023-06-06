package com.example.officialjavafxproj.Utils;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertBuilder {
    private Alert alert;
    private Alert.AlertType alertType = Alert.AlertType.NONE;

    private AlertBuilder(){
        alert = new Alert(alertType);
    }

    public static AlertBuilder builder(){
        return new AlertBuilder();
    }

    public AlertBuilder withType(Alert.AlertType alertType){
        alert.setAlertType(alertType);
        return this;
    }

    public AlertBuilder withButtonList(ObservableList<ButtonType> buttonLists){
        alert.getButtonTypes().setAll(buttonLists);
        return this;
    }

    public AlertBuilder withBodyText(String text){
        alert.setContentText(text);
        return this;
    }

    public AlertBuilder withHeaderText(String text){
        alert.setHeaderText(text);
        return this;
    }
    public ObservableList<ButtonType> getButtonTypes(){
        return alert.getButtonTypes();
    }

    public Alert build(){
        return alert;
    }
}
