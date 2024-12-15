package com.example.pathfinder.service.events;

import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {
  private final Long userId;
  private final String username;
  private final String email;

  public UserRegisteredEvent(Object source, Long userId, String username, String email) {
    super(source);
    this.userId = userId;
    this.username = username;
    this.email = email;
  }

  public Long getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }
}
