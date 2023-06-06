package com.example.officialjavafxproj.Controller;

import DataAccess.DataAccess;
import FileLocation.FileLocation;
import Middleware.InputMiddleware;
import Model.Account.GuestAccount;
import Model.Order.Cart;
import Model.User.Customer;
import Model.User.User;
import Service.UserCartServices;
import Service.UserServices;
import com.example.officialjavafxproj.Threads.UploadImageThread;
import com.example.officialjavafxproj.Utils.AlertBuilder;
import com.example.officialjavafxproj.Utils.FileController;
import com.example.officialjavafxproj.Utils.SceneController;
import com.example.officialjavafxproj.Utils.ToastBuilder;
import com.github.plushaze.traynotification.notification.Notifications;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RegisterControllers {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button registerButton;

    @FXML
    private Button toLoginButton;

    @FXML
    private TextField fullnameTextField;

    @FXML
    private TextField addressTextField;

    @FXML TextField phoneNumTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField confirmPassTextField;

    @FXML
    private Label registerMessage;

    @FXML
    private Label imageMessage;

    @FXML
    private Label fullNameErrorMessage;

    @FXML
    private Label addressErrorMessage;

    @FXML
    private Label phoneNumErrorMessage;

    @FXML
    private Label userNameErrorMessage;

    @FXML
    private Label passwordErrorMessage;

    @FXML
    private Label rePasswordErrorMessage;

    public void FieldOnReleased(){
        String userName = usernameTextField.getText();
        String fullName = fullnameTextField.getText();
        String phoneNum = phoneNumTextField.getText();
        String address = addressTextField.getText();
        String password = passwordTextField.getText();
        String rePass = confirmPassTextField.getText();
        boolean isDisabled = (userName.isEmpty() || userName.trim().isEmpty() ||
                fullName.isEmpty() || fullName.trim().isEmpty() ||
                password.isEmpty() || rePass.isEmpty() ||
                phoneNum.isEmpty() || phoneNum.trim().isEmpty() ||
                address.isEmpty() || address.trim().isEmpty());

        boolean isValid = (!InputMiddleware.isValidUsername(userName) ||
                !password.equals(rePass) ||
                !InputMiddleware.isValidIString(20, address) ||
                !InputMiddleware.isValidIString(15, fullName) ||
                !InputMiddleware.isValidPhoneNum(phoneNum) ||
                !InputMiddleware.isValidLowerBound(1, address) ||
                !InputMiddleware.isValidLowerBound(1, fullName));

        if(!InputMiddleware.isValidIString(15, fullName) || !InputMiddleware.isValidLowerBound(1, fullName)){
            fullNameErrorMessage.setText("Your full name must have <= 15 and >= 1 characters");
        }else {
            fullNameErrorMessage.setText("");
        }

        if(!InputMiddleware.isValidIString(20, address) || !InputMiddleware.isValidLowerBound(1, address)){
            addressErrorMessage.setText("Your address must have <= 20 and >= 1 characters");
        }else {
            addressErrorMessage.setText("");
        }

        if(!InputMiddleware.isValidPhoneNum(phoneNum)){
            phoneNumErrorMessage.setText("Your phone number must have 10 DIGITS");
        }else {
            phoneNumErrorMessage.setText("");
        }

        if(!InputMiddleware.isValidUsername(userName)){
            userNameErrorMessage.setText("Wrong format! Right format click ! to see");
        }else {
            userNameErrorMessage.setText("");
        }

        if(!InputMiddleware.isValidPassword(password) ){
            passwordErrorMessage.setText("Wrong format! Right format click ! to see");
        }else {
            passwordErrorMessage.setText("");
        }

        if(!password.equals(rePass)){
            rePasswordErrorMessage.setText("Your confirmation password must be the same");
        }else{
            rePasswordErrorMessage.setText("");
        }
        registerButton.setDisable(isDisabled || isValid);
    }

    public void onToLoginButton(ActionEvent event) throws IOException {
        SceneController.switchScene(event, "../Pages/login.fxml");
    }

    public void onImageUploadButton(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.gif", "*.jpg"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null){
            imageMessage.setText(file.getAbsolutePath());
        }else{
            imageMessage.setText("No file chosen");
        }
    }

    public void onExitButton(){
        DataAccess.transferAllData();
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();
    }

    public void onUsernameInfoButton(){
        Alert usernameInfo = AlertBuilder.builder()
                .withType(Alert.AlertType.INFORMATION)
                .withHeaderText("Username Right Format")
                .withBodyText("- Username must have more than 12 characters\n " +
                        "- No whitespaces accepted")
                .build();
        usernameInfo.show();
    }

    public void onPasswordInfoButton(){
        Alert usernameInfo = AlertBuilder.builder()
                .withType(Alert.AlertType.INFORMATION)
                .withHeaderText("Password Right Format")
                .withBodyText("- At least one uppercase letter\n" +
                        "- At least one lowercase letter\n" +
                        "- At least one number\n" +
                        "- At least one special character\n" +
                        "- Length must be 10 to 12 characters")
                .build();
        usernameInfo.show();

    }

    public void onRegisterButton(ActionEvent event) throws InterruptedException {
        String userName = usernameTextField.getText();
        String fullName = fullnameTextField.getText();
        String password = passwordTextField.getText();
        String rePass = confirmPassTextField.getText();
        String phoneNum = phoneNumTextField.getText();
        String address = addressTextField.getText();

        String targetFileDir = "";
        String ext = FileController.getFileExtension(new File(imageMessage.getText()));
        File targetFile = new File( FileLocation.getImageDir() + "Users/" + UserServices.builder().idCreation() + "." + ext);

        if(imageMessage.getText().equals("No file chosen") || imageMessage.getText().equals("")){
            targetFileDir = "Users/default.jpg";
        }else{
            targetFileDir = "Users/" + UserServices.builder().idCreation() + "." + ext;
        }
        UserServices userServices = UserServices.builder();
        //        UploadImageThread uploadThread = new UploadImageThread(targetFile, new File(imageMessage.getText()), 400, 400);
        UploadImageThread uploadThread = UploadImageThread
                .builder()
                .targetFile(targetFile)
                .uploadedFile(new File(imageMessage.getText()))
                .finalHeight(400)
                .finalWidth(400)
                .build();


        Thread imageThread = new Thread(uploadThread);
        if(!rePass.equals(password)){
            ToastBuilder.builder()
                    .withTitle("Register Message")
                    .withMessage("Not the same password!!!")
                    .withMode(Notifications.ERROR)
                    .show();
        }else{
            try{
                imageThread.start();
                userServices.register(new Customer(userServices.idCreation(), userName, password, fullName, address, phoneNum, 1000, new GuestAccount(), new Cart(UserCartServices.builder().idCreation(), userServices.idCreation()),targetFileDir));
                imageThread.join();
                ToastBuilder.builder()
                        .withTitle("Register Message")
                        .withMessage("Register Successfully!!!")
                        .withMode(Notifications.SUCCESS)
                        .show();
                SceneController.switchScene(event, "../Pages/userProfile.fxml");
            }catch (Error err){
                ToastBuilder.builder()
                        .withTitle("Register Message")
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
}
