package com.factory;

import com.factory.factory.NotificationFactory;
import com.factory.factory.NotificationFactoryRegistry;
import com.factory.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    private final NotificationFactoryRegistry registry = new NotificationFactoryRegistry();

    @Test
    void createsCorrectNotificationProducts() {
        assertEquals("EMAIL", registry.getFactory("email").createNotification().getType());
        assertEquals("SMS", registry.getFactory("sms").createNotification().getType());
        assertEquals("PUSH", registry.getFactory("push").createNotification().getType());
        assertEquals(3, registry.getSupportedTypes().size());
        assertEquals("EMAIL", registry.getFactory(" email ").createNotification().getType());
    }

    @Test
    void rejectsUnsupportedTypes() {
        assertThrows(IllegalArgumentException.class, () -> registry.getFactory("FAX"));
        assertThrows(IllegalArgumentException.class, () -> registry.getFactory(null));
    }

    @Test
    void rejectsBlankOrMissingNotificationFields() {
        NotificationController controller = new NotificationController(registry);
        for (Map<String, String> payload : java.util.List.of(
                Map.of("type", "SMS", "recipient", "  ", "message", "hello"),
                Map.of("type", "SMS", "recipient", "bob", "message", "  "),
                Map.of("type", "PUSH", "recipient", "bob")
        )) {
            assertEquals(400, controller.sendNotification(payload).getStatusCode().value());
        }
    }

    @Test
    void controllerCreatesProductOnceAndLabelsSimulation() {
        NotificationController controller = new NotificationController(registry);
        ResponseEntity<Map<String, Object>> response = controller.sendNotification(
                Map.of("type", "EMAIL", "recipient", "someone@example.com", "message", "hello"));
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("simulated", response.getBody().get("status"));
        assertEquals("EMAIL", response.getBody().get("notificationType"));
        assertTrue(response.getBody().get("result").toString().contains("SIMULATED"));
    }
}
