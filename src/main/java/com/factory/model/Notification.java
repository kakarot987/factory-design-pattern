package com.factory.model;

/**
 * PRODUCT INTERFACE — Factory Method Pattern
 *
 * This is the abstract "product" that all concrete notifications implement.
 * The factory will return objects of this type, hiding the concrete class.
 */
public interface Notification {
    String getType();
    String send(String recipient, String message);
    String getIcon();
}
