package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import Model.Product.Product;
import Service.ProductService;
import com.example.officialjavafxproj.Controller.Component.AdminProductController;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.SearchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminSortProductController implements Initializable,UIController {
    @FXML
    private GridPane gridPane;
    @FXML
    private VBox sortLayout;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button resetButton;
    @FXML
    private AnchorPane adminNavbar;
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

    public void resetToBegin(ActionEvent actionEvent) {
        searchTextField.setDisable(false);
        searchTextField.clear();
        loadPageContent();
    }

    public void addSortedPane() {
        try {
            sortLayout.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/sortPane.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageContent() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        if (ProductService.builder().getSortedProducts().size() == 0) {
            Label temp = new Label();
            temp.setText("No Products matched your requirement");
            gridPane.getChildren().add(temp);
        }
        for (Map.Entry<String, Product> product : ProductService.builder().getSortedProducts().entrySet()) {
            if(product.getKey().equals("deleted")){
                continue;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Component/adminViewProductComponent.fxml"));
                HBox productItem = fxmlLoader.load();
                AdminProductController adminProductController = fxmlLoader.getController();
                adminProductController.loadProductDisplay(product.getValue());
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.add(productItem, column, row++);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void loadSearchProducts() {
        gridPane.getChildren().clear();
        int column = 0;
        int row = 0;
        if (SearchController.getTempContainer().isEmpty()) {
            Label temp = new Label();
            temp.setText("No Products matched your requirement");
            gridPane.getChildren().add(temp);
        }
        for (Map.Entry<String, Product> product : SearchController.getTempContainer().entrySet()) {
            if(product.getKey().equals("deleted")){
                continue;
            }
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Component/adminViewProductComponent.fxml"));
                HBox productItem = fxmlLoader.load();
                AdminProductController adminProductController = fxmlLoader.getController();
                adminProductController.loadProductDisplay(product.getValue());
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.add(productItem, column, row++);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void search(ActionEvent actionEvent) {
        String search = searchTextField.getText().trim();
        if (!search.trim().isEmpty()) {

            if (ProductService.builder().getSortedProducts().isEmpty()) {
                SearchController.searchByIdentify(search, ProductService.builder().getAll());
                loadSearchProducts();
            } else {
                SearchController.searchByIdentify(search, ProductService.builder().getSortedProducts());
                loadSearchProducts();
            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        loadPageContent();
        addSortedPane();
    }
}
