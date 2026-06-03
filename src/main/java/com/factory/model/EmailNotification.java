package com.factory.model;

/**
 * CONCRETE PRODUCT — EmailNotification
 */
public class EmailNotification implements Notification {

    @Override
    public String getType() {
        return "EMAIL";
    }

    @Override
    public String send(String recipient, String message) {
        return String.format(
            "[EMAIL] To: %s | Subject: Notification | Body: %s | Via: SMTP Server",
            recipient, message
        );
    }

    @Override
    public String getIcon() {
        return "✉️";
    }
}
