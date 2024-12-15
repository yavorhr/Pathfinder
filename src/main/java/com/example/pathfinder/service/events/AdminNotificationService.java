package com.example.pathfinder.service.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminNotificationService {
  private final List<String> notifications;

  public AdminNotificationService() {
    this.notifications = new ArrayList<>();
  }

  @EventListener
  public void handleUserRegisteredEvent(UserRegisteredEvent event) {
    String notification = String.format("New user registered: Username : %s, Email: %s, Id: %s",
            event.getUsername(),
            event.getEmail(),
            event.getUserId());
    notifications.add(notification);
  }

  public List<String> getNotifications() {
    return new ArrayList<>(notifications); // Return a copy to prevent modification
  }

  public void clearNotifications() {
    notifications.clear();
  }
}
