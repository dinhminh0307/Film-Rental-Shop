package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import Model.Product.Product;
import Service.ProductService;
import com.example.officialjavafxproj.Controller.Component.AdminProductController;
import com.example.officialjavafxproj.Controller.Component.ProductComponentControllers;
import com.example.officialjavafxproj.Utils.SceneController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminViewProductController implements Initializable,UIController {
    @FXML
    private Label label;
    @FXML
    private AnchorPane adminNavbar;
    @FXML
    private RadioButton allProductButton;
    @FXML
    private RadioButton idButton;
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox sortLayout;
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
        try {
            adminNavbar.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavBarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addSortedPane(){
        try {
            sortLayout.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/sortPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageContent(){
            int column = 1;
            int row = 0;
            ProductService productService = ProductService.builder();
            gridPane.getChildren().clear();
            for(Map.Entry<String, Product> product : productService.getAll().entrySet()){
                if(product.getKey().equals("deleted")){
                    continue;
                }
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Component/adminViewProductComponent.fxml"));
                    HBox productItem = loader.load();
                    AdminProductController adminProductController = loader.getController();
                    adminProductController.loadProductDisplay(product.getValue());
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);
                    gridPane.add(productItem,column,row++);
                }
                catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
    }
    public void addProductButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/adminAddProduct.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        addSortedPane();
        loadPageContent();
    }
}
