package com.factory.model;

/**
 * CONCRETE PRODUCT — SmsNotification
 */
public class SmsNotification implements Notification {

    @Override
    public String getType() {
        return "SMS";
    }

    @Override
    public String send(String recipient, String message) {
        // SMS messages are typically truncated to 160 chars
        String truncated = message.length() > 160 ? message.substring(0, 157) + "..." : message;
        return String.format(
            "[SMS] To: %s | Chars: %d/160 | Text: %s | Via: Twilio Gateway",
            recipient, truncated.length(), truncated
        );
    }

    @Override
    public String getIcon() {
        return "📱";
    }
}
