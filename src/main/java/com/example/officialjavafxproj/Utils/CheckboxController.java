package com.example.officialjavafxproj.Utils;

import javafx.scene.control.CheckBox;

import java.util.ArrayList;

public class CheckboxController {
    public static String[] getAllOptions(ArrayList<CheckBox> checkboxes){
        String[] options = new String[checkboxes.size()];
        int index = 0;
        for(CheckBox checkBox : checkboxes){
            if(checkBox.isSelected()){
                options[index] = checkBox.getText();

            }else {
                options[index] = "NONE";
            }
            index++;
        }
        return options;
    }
}
