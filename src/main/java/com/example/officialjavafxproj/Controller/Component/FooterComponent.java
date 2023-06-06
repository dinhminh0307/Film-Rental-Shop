package com.example.officialjavafxproj.Controller.Component;

import com.example.officialjavafxproj.Utils.AlertBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class FooterComponent {

    public void onViewInfoButton(ActionEvent event) {
        Alert showProjectInfo = AlertBuilder.builder()
                .withType(Alert.AlertType.INFORMATION)
                .withBodyText("Our application is a website for a local video rental shop. This website allows users to rent, borrow and return a variety of products including videos, games, movie records, and DVDs. You can access our website as a Guest, which would have the least actions on our websites. However, as soon as you register, you are able to rent and borrow more products for a longer period of time. VIP is the highest customer we have with the most rights. Apart from having basic features, we allow our users to be able to search for items, sort items, and many more features. \n" +
                        "The website's admin can manage all the users, products, and orders of the website. Similar to the customer, the admin can search, sort, or separate those into different categories." + "\n" + "Rewarding System: " + "\n" +
                        "When you register, you account rank will start from Guest Account.\n" +
                        "1. When you return more than 3 items you account can be promoted to Regular \n" +
                        "2. When you return more than 5 items you account can be promoted to VIP \n" +
                        "3. When you are VIP, each return Item will reward you 10 points\n" +
                        "4. VIP account can rent an item for free when they have more than 100 points")
                .withHeaderText("Project Information")
                .build();
        showProjectInfo.getDialogPane().setPrefHeight(500);
        showProjectInfo.getDialogPane().setPrefWidth(500);
        showProjectInfo.show();
    }
}
