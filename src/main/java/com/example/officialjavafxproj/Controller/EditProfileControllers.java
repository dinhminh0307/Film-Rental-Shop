package com.example.officialjavafxproj.Controller;

import FileLocation.FileLocation;
import Middleware.InputMiddleware;
import Model.Account.Account;
import Model.User.User;
import Service.UserServices;
import com.example.officialjavafxproj.Threads.UploadImageThread;
import com.example.officialjavafxproj.Utils.FileController;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import java.util.Formattable;
import java.util.ResourceBundle;

public class EditProfileControllers implements Initializable,UIController {

    @FXML
    private AnchorPane navbarPane;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button uploadImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button resetToBeginButton;
    @FXML
    private TextField fullNameEditTextField;
    @FXML
    private TextField addressEditTextField;
    @FXML
    private TextField phoneNumEditTextField;

    @FXML
    private Label fullNameWarningMessage;

    @FXML
    private Label addressWarningMessage;

    @FXML
    private Label phoneNumWarningMessage;

    @FXML
    private Label imageMessage;

    private String newImageDir;

    private User currentUser = UserServices.builder().getCurrentUser();

    @FXML
    private AnchorPane footerPane;

    public void addFooterBar(){
        try {
            footerPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/footer.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onFieldReleased() {
        String fullName = fullNameEditTextField.getText();
        String phoneNum = phoneNumEditTextField.getText();
        String address = addressEditTextField.getText();

        boolean isValid = (InputMiddleware.isValidIString(20, address) &&
                InputMiddleware.isValidIString(15, fullName) &&
                InputMiddleware.isValidPhoneNum(phoneNum) &&
                InputMiddleware.isValidLowerBound(1, address) &&
                InputMiddleware.isValidLowerBound(1, fullName));

        saveButton.setDisable(!isValid);

        if(!InputMiddleware.isValidIString(15, fullName) || !InputMiddleware.isValidLowerBound(1, fullName)){
            fullNameWarningMessage.setText("Your full name must have <= 15 and >= 1 characters");
        }else {
            fullNameWarningMessage.setText("");
        }

        if(!InputMiddleware.isValidIString(20, address) || !InputMiddleware.isValidLowerBound(1, address)){
            addressWarningMessage.setText("Your address must have <= 20 and >= 1 characters");
        }else {
            addressWarningMessage.setText("");
        }

        if(!InputMiddleware.isValidPhoneNum(phoneNum)){
            phoneNumWarningMessage.setText("Your phone number must have 10 DIGITS");
        }else {
            phoneNumWarningMessage.setText("");
        }
    }

    public void onSaveProfile(ActionEvent event) {
        currentUser.setFullName(fullNameEditTextField.getText());
        currentUser.setAddress(addressEditTextField.getText());
        currentUser.setPhoneNum(phoneNumEditTextField.getText());
        if(!imageMessage.getText().equals("")){
            if(!imageMessage.getText().equals("No file chosen")){
                File renameFile = new File(FileLocation.getImageDir() + currentUser.getImageLocation());
                String ext = FileController.getFileExtension(renameFile);
                renameFile = new File(FileLocation.getImageDir() + "Users/" + currentUser.getUserId() + "." + ext);
                File file = new File(newImageDir);
                FileController.deleteFile(renameFile);
                currentUser.setImageLocation("Users/" + currentUser.getUserId() + "." + ext);
                FileController.renameFile(file, renameFile);
            }
        }
        try {
            SceneController.switchScene(event, "../Pages/userProfile.fxml");
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
        String profileImgUrl = FileLocation.getImageDir() + currentUser.getImageLocation();
        try {
            Image currentUserImage = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
            profileImage.setImage(currentUserImage);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        fullNameEditTextField.setText(currentUser.getFullName());
        addressEditTextField.setText(currentUser.getAddress());
        phoneNumEditTextField.setText(currentUser.getPhoneNum());

    }

    public void onToProfileButton(ActionEvent actionEvent) throws IOException{
        SceneController.switchScene(actionEvent, "../Pages/userProfile.fxml");
    }


    public void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            imageMessage.setText(file.getAbsolutePath());
            String ext = FileController.getFileExtension(new File(imageMessage.getText()));
            newImageDir = FileLocation.getImageDir() + currentUser.getImageLocation() + "Backup" + "." + ext;
            File targetFile = new File( newImageDir);
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
                    Image uploadUserImage = new Image(new FileInputStream(newImageDir), 400, 400, false, false);
                    profileImage.setImage(uploadUserImage);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }else{
            imageMessage.setText("No file chosen");
        }
    }

    public void addNavigationBar(){
        try {
            navbarPane.getChildren().add(SceneController.getComponentScene(new AnchorPane(), "../Component/navbarComponent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageContent(){
        String profileImgUrl = FileLocation.getImageDir() + currentUser.getImageLocation();
        try {
            Image currentUserImage = new Image(new FileInputStream(profileImgUrl), 400, 400, false, false);
            profileImage.setImage(currentUserImage);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        fullNameEditTextField.setText(currentUser.getFullName());
        addressEditTextField.setText(currentUser.getAddress());
        phoneNumEditTextField.setText(currentUser.getPhoneNum());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addNavigationBar();
        loadPageContent();
        addFooterBar();
    }
}
