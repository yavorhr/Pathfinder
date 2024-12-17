package com.example.pathfinder.model.view;

import java.time.LocalDateTime;

public class NotificationViewModel {
  private String email;
  private String username;
  private LocalDateTime registeredOn;
  private LocalDateTime modified;

  public NotificationViewModel() {
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public LocalDateTime getRegisteredOn() {
    return registeredOn;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public NotificationViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public NotificationViewModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public NotificationViewModel setRegisteredOn(LocalDateTime registeredOn) {
    this.registeredOn = registeredOn;
    return this;
  }

  public NotificationViewModel setModified(LocalDateTime modified) {
    this.modified = modified;
    return this;
  }
}
