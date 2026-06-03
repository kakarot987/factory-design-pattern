package com.factory.factory;

import com.factory.model.EmailNotification;
import com.factory.model.Notification;
import com.factory.model.PushNotification;
import com.factory.model.SmsNotification;

/**
 * CONCRETE CREATOR — EmailNotificationFactory
 * Overrides the factory method to return an EmailNotification.
 */
class EmailNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new EmailNotification();  // Only place EmailNotification is instantiated
    }
}

/**
 * CONCRETE CREATOR — SmsNotificationFactory
 * Overrides the factory method to return an SmsNotification.
 */
class SmsNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new SmsNotification();
    }
}

/**
 * CONCRETE CREATOR — PushNotificationFactory
 * Overrides the factory method to return a PushNotification.
 */
class PushNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new PushNotification();
    }
}
