package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Middleware.InputMiddleware;
import Model.Account.GuestAccount;
import Model.Order.Cart;
import Model.Product.DVD;
import Model.Product.Game;
import Model.Product.MRecords;
import Model.Product.Product;
import Model.User.Customer;
import Service.ProductService;
import Service.UserCartServices;
import Service.UserServices;
import com.example.officialjavafxproj.Threads.UploadImageThread;
import com.example.officialjavafxproj.Utils.*;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminAddProductController implements Initializable,UIController {
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

    ObservableList<String> rentalTypeList = FXCollections.observableArrayList(Product.getRentalTypes());
    ObservableList<String> genreTypeList = FXCollections.observableArrayList(Product.getGenres());
    ObservableList<String> loanTypeList = FXCollections.observableArrayList(Product.getLoanTypes());

    private String imageDir;
    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNavigationBar() {
        try {
            adminNavbar.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/adminNavBarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadPageContent() {
    }

    public void setChoiceBox() {
        rentalTypeChoiceBox.setItems(rentalTypeList);
        genreTypeChoiceBox.setItems(genreTypeList);
        loanTypeChoiceBox.setItems(loanTypeList);
    }

    public void onFieldReleased() {
        String productName = nameTextField.getText();
        String productPrice = priceTextField.getText();
        String numOfCopies = copiesTextField.getText();
        String publishedYear = publishedYearTextField.getText();


        boolean isValid = (!productName.trim().isEmpty() && InputMiddleware.isPositive(productPrice) && InputMiddleware.isPositive(numOfCopies) && InputMiddleware.isPositive(publishedYear) && genreTypeChoiceBox.getValue() != null && rentalTypeChoiceBox.getValue() != null && loanTypeChoiceBox.getValue() != null);

        saveButton.setDisable(!isValid);


        if (productName.trim().isEmpty()) {
            nameWarningMessage.setText("You must not leave this field empty");
        } else {
            nameWarningMessage.setText("");
        }
        if (productPrice.trim().isEmpty()) {
            priceWarningMessage.setText("You must not leave this field empty");
        } else if (!InputMiddleware.isValidNumber(productPrice)) {
            priceWarningMessage.setText("The price must be a number");
        } else if (!InputMiddleware.isPositive(productPrice)) {
            priceWarningMessage.setText("The price must be positive");
        } else {
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
        if (numOfCopies.trim().isEmpty()) {
            copiesWarningMessage.setText("You must not leave this field empty");
        } else if (!InputMiddleware.isValidCopy(numOfCopies)) {
            copiesWarningMessage.setText("Num of copies must be a number");
        } else if (!InputMiddleware.isPositive(numOfCopies)) {
            copiesWarningMessage.setText("Num of copies must be positive");
        } else {
            copiesWarningMessage.setText("");
        }
    }

    public void setDisableButton(MouseEvent mouseEvent) {
        if (rentalTypeChoiceBox.getValue() == null || genreTypeChoiceBox.getValue() == null || loanTypeChoiceBox.getValue() == null) {
            saveButton.setDisable(true);
        } else {
            saveButton.setDisable(false);
        }
    }

    public void onResetToBegin() {
        nameTextField.setText("");
        priceTextField.setText("");
        copiesTextField.setText("");
        publishedYearTextField.setText("");
        rentalTypeChoiceBox.setValue(null);
        genreTypeChoiceBox.setValue(null);
        loanTypeChoiceBox.setValue(null);
    }

    public void back(ActionEvent actionEvent) throws IOException {
        SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
    }

    public void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imageMessage.setText(file.getAbsolutePath());
            String ext = FileController.getFileExtension(new File(imageMessage.getText()));
            imageDir = FileLocation.getImageDir() + "." + ext;
            File targetFile = new File(imageDir);
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
                    Image uploadProductImage = new Image(new FileInputStream(imageDir), 400, 400, false, false);
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
    }

    public void onSaveInformation(ActionEvent actionEvent) throws InterruptedException {
        String productTitle = nameTextField.getText();
        double productRentalFee = Double.parseDouble(priceTextField.getText());
        int productCopies = Integer.parseInt(copiesTextField.getText());
        String productGenre = genreTypeChoiceBox.getValue();
        String productLoanType = loanTypeChoiceBox.getValue();
        String productRentalType = rentalTypeChoiceBox.getValue();
        String publishedYear = publishedYearTextField.getText();
        String targetFileDir = "";
        String ext = FileController.getFileExtension(new File(imageMessage.getText()));
        File targetFile = new File(FileLocation.getImageDir() + "Product/" + ProductService.builder().idCreation() + publishedYear + "." + ext);

        if (imageMessage.getText().equals("No file chosen") || imageMessage.getText().equals("")) {
            targetFileDir = "Product/default.png";
        } else {
            targetFileDir = "Product/" + ProductService.builder().idCreation() + publishedYear + "." + ext;
        }
        ProductService productService = ProductService.builder();
        //        UploadImageThread uploadThread = new UploadImageThread(targetFile, new File(imageMessage.getText()), 400, 400);
        UploadImageThread uploadThread = UploadImageThread
                .builder()
                .targetFile(targetFile)
                .uploadedFile(new File(imageMessage.getText()))
                .finalHeight(400)
                .finalWidth(400)
                .build();


        Thread imageThread = new Thread(uploadThread);
        boolean isDuplicated = false;
        for (Map.Entry<String, Product> product : productService.getAll().entrySet()) {
            if (product.getValue().getTitle().equals(productTitle)) {
                isDuplicated = true;
            }
        }
        if (isDuplicated == true) {
            Alert saveAlert = AlertBuilder.builder()
                    .withType(Alert.AlertType.WARNING)
                    .withBodyText("The title is existed, do you want to add")
                    .withHeaderText("Save warning")
                    .withButtonList(AlertButtonController.getSaveButtonTypes())
                    .build();
            ObservableList<ButtonType> saveButtons = saveAlert.getButtonTypes();
            Optional<ButtonType> choice = saveAlert.showAndWait();
            if (choice.get() == saveButtons.get(0)) {
                saveAlert.close();
            } else {
                try {
                    imageThread.start();
                    if (productRentalType.equals("DVD")) {
                        productService.add(new DVD(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                    } else if (productRentalType.equals("GAME")) {
                        productService.add(new Game(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                    } else {
                        productService.add(new MRecords(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                    }
                    imageThread.join();
                    ToastBuilder.builder()
                            .withTitle("Add Message")
                            .withMessage("Add Successfully!!!")
                            .withMode(Notifications.SUCCESS)
                            .show();
                    SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
                } catch (Error err) {
                    ToastBuilder.builder()
                            .withTitle("Add Message")
                            .withMessage(err.getMessage())
                            .withMode(Notifications.ERROR)
                            .show();
                    imageThread.join();
                    FileController.deleteFile(targetFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            try {
                imageThread.start();
                if (productRentalType.equals("DVD")) {
                    productService.add(new DVD(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                } else if (productRentalType.equals("GAME")) {
                    productService.add(new Game(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                } else {
                    productService.add(new MRecords(productService.idCreation() + publishedYear, productTitle, productRentalType, productGenre, publishedYear, productCopies, productRentalFee, productLoanType, "AVAILABLE", targetFileDir));
                }
                imageThread.join();
                ToastBuilder.builder()
                        .withTitle("Add Message")
                        .withMessage("Add Successfully!!!")
                        .withMode(Notifications.SUCCESS)
                        .show();
                SceneController.switchScene(actionEvent, "../Pages/adminViewProduct.fxml");
            } catch (Error err) {
                ToastBuilder.builder()
                        .withTitle("Add Message")
                        .withMessage(err.getMessage())
                        .withMode(Notifications.ERROR)
                        .show();
                imageThread.join();
                FileController.deleteFile(targetFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addFooterBar();
        addNavigationBar();
        setChoiceBox();
    }
}
