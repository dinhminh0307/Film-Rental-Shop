package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import Middleware.OrderMiddleware;
import Model.Order.Order;
import Model.Order.OrderDetail;
import Service.OrderCustomerService;
import Service.OrderDetailCartService;
import Service.UserServices;
import com.example.officialjavafxproj.Controller.Component.CartComponentControllers;
import com.example.officialjavafxproj.Controller.Component.OrderComponentControllers;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


public class UserOrdersControllers implements Initializable,UIController {
    @FXML
    private AnchorPane navbarPane;

    @FXML
    private VBox ordersDisplay;

    @FXML
    private Label ordersQuantityDisplay;

    @FXML
    private AnchorPane footerPane;

    private final OrderCustomerService orderCustomerService = OrderCustomerService.builder();

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addNavigationBar() {
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/navbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageContent(){
        ordersQuantityDisplay.setText(String.valueOf(orderCustomerService.getAll().size()));
        if(orderCustomerService.getAll().size() == 0){
            UserServices.builder().getCurrentUser().getAccount().setCurrentlyBorrowed(false);
        }else{
            UserServices.builder().getCurrentUser().getAccount().setCurrentlyBorrowed(true);

        }
        if(orderCustomerService.getAll().size() == 0){
            Label messageLabel = new Label();
            messageLabel.setText("You have not order anything yet");
            ordersDisplay.getChildren().add(messageLabel);
        }else{
            for (Map.Entry<String, Order> order : orderCustomerService.getAll().entrySet()) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../Component/orderComponent.fxml"));
                    HBox orderItem = fxmlLoader.load();
                    OrderComponentControllers orderComponentControllers = fxmlLoader.getController();
                    orderComponentControllers.loadOrderData(order.getValue());
                    ordersDisplay.getChildren().add(orderItem);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void onBackToShopping(ActionEvent event) throws IOException{
        SceneController.switchScene(event, "../Pages/homepage.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        loadPageContent();
        addFooterBar();
    }
}
