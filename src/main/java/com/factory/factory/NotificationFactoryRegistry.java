package com.factory.factory;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * FACTORY REGISTRY
 *
 * This resolves a string type ("EMAIL", "SMS", "PUSH") to the correct factory.
 * It's a common pattern to combine with Factory Method for runtime selection.
 *
 * This keeps the controller clean — it never knows about concrete classes.
 */
@Component
public class NotificationFactoryRegistry {

    private final Map<String, NotificationFactory> registry = Map.of(
        "EMAIL", new EmailNotificationFactory(),
        "SMS",   new SmsNotificationFactory(),
        "PUSH",  new PushNotificationFactory()
    );

    public NotificationFactory getFactory(String type) {
        NotificationFactory factory = type == null ? null : registry.get(type.toUpperCase(java.util.Locale.ROOT));
        if (factory == null) {
            throw new IllegalArgumentException(
                "Unknown notification type: '" + type + "'. Valid types: EMAIL, SMS, PUSH"
            );
        }
        return factory;
    }

    public java.util.Set<String> getSupportedTypes() {
        return registry.keySet();
    }
}
