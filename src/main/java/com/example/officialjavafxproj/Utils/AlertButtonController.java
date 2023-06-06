package com.example.officialjavafxproj.Utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;

public class AlertButtonController {

    public static ObservableList<ButtonType> getCartButtonTypes(){
        ButtonType cashButton = new ButtonType("Pay By Cash");
        ButtonType creditButton = new ButtonType("Pay By Credit");
        ButtonType cancel = new ButtonType("Cancel");

        ObservableList<ButtonType> buttonTypes = FXCollections.observableArrayList();
        buttonTypes.addAll(cashButton, creditButton, cancel);
        return buttonTypes;
    }
    public static ObservableList<ButtonType> getSaveButtonTypes(){
        ButtonType continueButton = new ButtonType("Continue adding");
        ButtonType saveButton = new ButtonType("Save");

        ObservableList<ButtonType> buttonTypes = FXCollections.observableArrayList();
        buttonTypes.addAll(continueButton, saveButton);
        return buttonTypes;
    }
    public static ObservableList<ButtonType> getDeleteButtonTypes(){
        ButtonType cancelButton = new ButtonType("Cancel");
        ButtonType deleteButton = new ButtonType("Delete");

        ObservableList<ButtonType> buttonTypes = FXCollections.observableArrayList();
        buttonTypes.addAll(cancelButton,deleteButton);
        return buttonTypes;
    }
}
