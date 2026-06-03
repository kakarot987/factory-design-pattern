package com.factory.model;

/**
 * CONCRETE PRODUCT — PushNotification
 */
public class PushNotification implements Notification {

    @Override
    public String getType() {
        return "PUSH";
    }

    @Override
    public String send(String recipient, String message) {
        return String.format(
            "[PUSH] Device: %s | Title: App Notification | Body: %s | Via: FCM/APNs",
            recipient, message
        );
    }

    @Override
    public String getIcon() {
        return "🔔";
    }
}
