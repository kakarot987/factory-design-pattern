package com.factory.factory;

import com.factory.model.Notification;

/**
 * ABSTRACT CREATOR — NotificationFactory
 *
 * This is the heart of the Factory Method Pattern.
 *
 * - Declares the factory method `createNotification()` — subclasses override it.
 * - Contains `notify()` which is the "template" that uses the factory method.
 *   Callers only interact with this method; they never instantiate products directly.
 *
 * KEY PRINCIPLE: The creator delegates object instantiation to subclasses.
 * The base class defines WHAT to do (send a notification),
 * subclasses decide HOW to create the product.
 */
public abstract class NotificationFactory {

    /**
     * THE FACTORY METHOD — subclasses must override this.
     * Returns a concrete Notification without exposing the class name to callers.
     */
    public abstract Notification createNotification();

    /**
     * Business logic that uses the factory method.
     * Notice: this method doesn't know which concrete Notification it gets.
     */
    public String notify(String recipient, String message) {
        // Factory method call — polymorphism at work
        Notification notification = createNotification();

        String result = notification.send(recipient, message);

        return String.format("%s %s | Status: SENT ✓", notification.getIcon(), result);
    }

    public String getFactoryName() {
        return this.getClass().getSimpleName();
    }
}
