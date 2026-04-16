package com.vaadin.demo.ui.util;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class Notifications {

    private Notifications() {}

    public static void show(String message, NotificationVariant... variants) {
        Notification notification = Notification.show(message, 3000, Notification.Position.BOTTOM_END);
        notification.addThemeVariants(variants);
    }
}
