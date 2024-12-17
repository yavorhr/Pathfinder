package com.example.pathfinder.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
  private String email;
  private String username;
  private LocalDateTime registeredOn;
  private LocalDateTime modified;

  public Notification() {
  }

  @Column(nullable = false, unique = true)
  public String getEmail() {
    return email;
  }

  @Column(nullable = false, unique = true)
  public String getUsername() {
    return username;
  }

  @Column
  public LocalDateTime getRegisteredOn() {
    return registeredOn;
  }

  @Column
  public LocalDateTime getModified() {
    return modified;
  }

  public Notification setEmail(String email) {
    this.email = email;
    return this;
  }

  public Notification setUsername(String username) {
    this.username = username;
    return this;
  }

  public Notification setRegisteredOn(LocalDateTime registeredOn) {
    this.registeredOn = registeredOn;
    return this;
  }

  public Notification setModified(LocalDateTime modified) {
    this.modified = modified;
    return this;
  }
}
