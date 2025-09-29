package com.example.pathfinder.service.events;

import org.springframework.context.ApplicationEvent;
import java.time.LocalDateTime;

public class UserRegisteredEvent extends ApplicationEvent {
  private final String username;
  private final String email;

  public UserRegisteredEvent(Object source, LocalDateTime registeredOn, String username, String email) {
    super(source);
    this.username = username;
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}
