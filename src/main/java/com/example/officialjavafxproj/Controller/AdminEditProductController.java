package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Middleware.InputMiddleware;
import Model.Product.Product;
import Service.ProductService;
import com.example.officialjavafxproj.Threads.UploadImageThread;
import com.example.officialjavafxproj.Utils.FileController;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminEditProductController implements Initializable,UIController {
    @FXML
    private AnchorPane adminNavbar;
    @FXML
    private ImageView productImage;
    @FXML
    private Button uploadImageButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button resetButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField priceTextField;
    @FXML
    private TextField copiesTextField;
    @FXML
    private TextField publishedYearTextField;
    @FXML
    private Label nameWarningMessage;
    @FXML
    private Label priceWarningMessage;
    @FXML
    private Label copiesWarningMessage;
    @FXML
    private Label publishedYearWarningMessage;
    @FXML
    private Label imageMessage;
    @FXML
    private Button backButton;
    @FXML
    private ChoiceBox<String> rentalTypeChoiceBox;
    @FXML
    private ChoiceBox<String> genreTypeChoiceBox;
    @FXML
    private ChoiceBox<String> loanTypeChoiceBox;

    private final Product product =  ProductService.builder().getTargetProduct();
    ObservableList<String> rentalTypeList = FXCollections.observableArrayList(Product.getRentalTypes());
    ObservableList<String> genreTypeList = FXCollections.observableArrayList(Product.getGenres());
    ObservableList<String> loanTypeList = FXCollections.observableArrayList(Product.getLoanTypes());
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

    public void setChoiceBox(){
        rentalTypeChoiceBox.setItems(rentalTypeList);
        genreTypeChoiceBox.setItems(genreTypeList);
        loanTypeChoiceBox.setItems(loanTypeList);
    }

    private String newImageDir;



    public void onFieldReleased() {
        String productName = nameTextField.getText();
        String productPrice = priceTextField.getText();
        String numOfCopies = copiesTextField.getText();
        String publishedYear = publishedYearTextField.getText();

        boolean isValid = (!productName.trim().isEmpty() && InputMiddleware.isPositive(productPrice) && InputMiddleware.isPositive(numOfCopies) && InputMiddleware.isPositive(publishedYear));

        saveButton.setDisable(!isValid);

        if(productName.trim().isEmpty()){
            nameWarningMessage.setText("You must not leave this field empty");
        }
        else {
            nameWarningMessage.setText("");
        }
        if(productPrice.trim().isEmpty()){
            priceWarningMessage.setText("You must not leave this field empty");
        }
        else if(!InputMiddleware.isValidNumber(productPrice)){
            priceWarningMessage.setText("The price must be a number");
        }
        else if(!InputMiddleware.isPositive(productPrice)){
            priceWarningMessage.setText("The price must be positive");
        }
        else {
            priceWarningMessage.setText("");
        }
        if (publishedYear.trim().isEmpty()) {
            publishedYearWarningMessage.setText("You must not leave this field empty");
        } else if (!InputMiddleware.isValidNumber(publishedYear)) {
            publishedYearWarningMessage.setText("The published year must be a number");
        } else if (publishedYear.length() != 4) {
            publishedYearWarningMessage.setText("The year is not valid");
        } else if (!InputMiddleware.isPositive(publishedYear)) {
            publishedYearWarningMessage.setText("The published year must be positive");
        } else {
            publishedYearWarningMessage.setText("");
        }
        if(numOfCopies.trim().isEmpty()){
            copiesWarningMessage.setText("You must not leave this field empty");
        } else if (!InputMiddleware.isValidCopy(numOfCopies)) {
            copiesWarningMessage.setText("Num of copies must be a number");
        } else if(!InputMiddleware.isPositive(numOfCopies)){
            copiesWarningMessage.setText("Num of copies must be positive");
        }
        else {
            copiesWarningMessage.setText("");
        }
    }
    public void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageMessage.setText(file.getAbsolutePath());
            String ext = FileController.getFileExtension(new File(imageMessage.getText()));
            newImageDir = FileLocation.getImageDir() + product.getImageLocation() + "Backup" + "." + ext;
            File targetFile = new File(newImageDir);
            UploadImageThread uploadThread = UploadImageThread
                    .builder()
                    .targetFile(targetFile)
                    .uploadedFile(new File(imageMessage.getText()))
                    .finalHeight(400)
                    .finalWidth(400)
                    .build();

            Thread imageThread = new Thread(uploadThread);
            imageThread.start();
            try {
                imageThread.join();
                try {
                    Image uploadProductImage = new Image(new FileInputStream(newImageDir), 400, 400, false, false);
                    productImage.setImage(uploadProductImage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } else {
            imageMessage.setText("No file chosen");
        }
        System.out.println(newImageDir);
    }
    public void onSaveInformation(ActionEvent actionEvent){
        product.setTitle(nameTextField.getText());
        product.setRentalFee(Double.parseDouble(priceTextField.getText()));
        product.setNumOfCopies(Integer.parseInt(copiesTextField.getText()));
        product.setGenre(genreTypeChoiceBox.getValue());
        product.setLoanType(loanTypeChoiceBox.getValue());
        product.setRentalType(rentalTypeChoiceBox.getValue());
        product.setPublishedYear(publishedYearTextField.getText());
        if(copiesTextField.getText().equals("0")){
            product.setStatus("BORROWED");
        }else{
            product.setStatus("AVAILABLE");
        }
        if(!imageMessage.getText().equals("")){
            if(!imageMessage.getText().equals("No file chosen")){
                File renameFile = new File(FileLocation.getImageDir() + product.getImageLocation());
                String ext = FileController.getFileExtension(renameFile);
                renameFile = new File(FileLocation.getImageDir() + "Product/" + product.getId() + "." + ext);
                File file = new File(newImageDir);
                FileController.deleteFile(renameFile);
                product.setImageLocation("Product/" + product.getId()  + "." + ext);
                FileController.renameFile(file, renameFile);
            }
        }
        try {
            SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ToastBuilder.builder()
                .withTitle("Changed Successfully")
                .withMessage("You information has changed successfully")
                .withMode(Notifications.SUCCESS)
                .show();

    }

    public void onResetToBegin() {
        String profileImgUrl = FileLocation.getImageDir() + product.getImageLocation();
        try {
            Image currentUserImage = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
            productImage.setImage(currentUserImage);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        nameWarningMessage.setText("");
        priceWarningMessage.setText("");
        copiesWarningMessage.setText("");
        publishedYearWarningMessage.setText("");
        nameTextField.setText(product.getTitle());
        priceTextField.setText(String.valueOf(product.getRentalFee()));
        copiesTextField.setText(String.valueOf(product.getNumOfCopies()));
        publishedYearTextField.setText(product.getPublishedYear());
        rentalTypeChoiceBox.setValue(product.getRentalType());
        genreTypeChoiceBox.setValue(product.getGenre());
        loanTypeChoiceBox.setValue(product.getLoanType());
    }
    public void loadPageContent() {
        String profileImgUrl = FileLocation.getImageDir() + product.getImageLocation();
        try {
            Image currentUserImage = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
            productImage.setImage(currentUserImage);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        nameTextField.setText(product.getTitle());
        priceTextField.setText(String.valueOf(product.getRentalFee()));
        copiesTextField.setText(String.valueOf(product.getNumOfCopies()));
        publishedYearTextField.setText(product.getPublishedYear());
        genreTypeChoiceBox.setValue(product.getGenre());
        loanTypeChoiceBox.setValue(product.getLoanType());
        rentalTypeChoiceBox.setValue(product.getRentalType());
    }
    public void back(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        loadPageContent();
        setChoiceBox();
    }
}
