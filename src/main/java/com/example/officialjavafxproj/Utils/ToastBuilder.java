package com.example.officialjavafxproj.Utils;

import com.github.plushaze.traynotification.animations.Animations;
import com.github.plushaze.traynotification.notification.Notification;
import com.github.plushaze.traynotification.notification.TrayNotification;
import javafx.util.Duration;


public class ToastBuilder {
        private TrayNotification notification;

        private ToastBuilder() {
            notification = new TrayNotification();
        }

        public static ToastBuilder builder() {
            return new ToastBuilder();
        }

        public ToastBuilder withTitle(String title){
            notification.setTitle(title);
            return this;
        }

        public ToastBuilder withMessage(String message){
            notification.setMessage(message);
            return this;
        }

        public ToastBuilder withMode(Notification mode){
            notification.setNotification(mode);
            return this;
        }

        public void show(){
            notification.setAnimation(Animations.POPUP);
            notification.showAndDismiss(Duration.millis(1000));
        }
}