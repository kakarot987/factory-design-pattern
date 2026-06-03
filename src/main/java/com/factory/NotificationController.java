package com.factory;

import com.factory.factory.NotificationFactory;
import com.factory.factory.NotificationFactoryRegistry;
import com.factory.model.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/notify")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationFactoryRegistry registry;

    public NotificationController(NotificationFactoryRegistry registry) {
        this.registry = registry;
    }

    /**
     * POST /api/notify
     * Body: { "type": "EMAIL", "recipient": "alice@example.com", "message": "Hello!" }
     *
     * The controller has ZERO knowledge of concrete notification classes.
     * It only talks to the abstract NotificationFactory.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> sendNotification(@RequestBody Map<String, String> request) {
        String type      = request.getOrDefault("type", "");
        String recipient = request.getOrDefault("recipient", "");
        String message   = request.getOrDefault("message", "");

        if (type.isBlank() || recipient.isBlank() || message.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Fields 'type', 'recipient', and 'message' are required."
            ));
        }

        try {
            // 1. Get the right factory (still abstracted — no `new` here)
            NotificationFactory factory = registry.getFactory(type);

            // 2. Factory creates the product internally
            Notification notification = factory.createNotification();

            // 3. Use the factory's template method
            String result = factory.notify(recipient, message);

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("status", "success");
            response.put("notificationType", notification.getType());
            response.put("icon", notification.getIcon());
            response.put("factoryUsed", factory.getFactoryName());
            response.put("result", result);
            response.put("patternNote",
                "The controller only called factory.notify(). " +
                "It never instantiated " + notification.getClass().getSimpleName() + " directly."
            );

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** GET /api/notify/types — list available notification types */
    @GetMapping("/types")
    public ResponseEntity<Map<String, Object>> getTypes() {
        Set<String> types = registry.getSupportedTypes();
        return ResponseEntity.ok(Map.of(
            "supportedTypes", types,
            "pattern", "Factory Method",
            "description", "Each type has its own ConcreteFactory that overrides createNotification()"
        ));
    }
}
