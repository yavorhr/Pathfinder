package com.example.pathfinder.model.view;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;

import java.time.LocalDateTime;
import java.util.Set;

public class UserNotificationViewModel {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime registrationDate;
  private LocalDateTime lastModifiedTime;
  private boolean accountLocked;
  private LocalDateTime disabledTime;
  private Integer timesLocked;
  private Set<UserRoleEnum> roles;
  private boolean isEnabled;

  public UserNotificationViewModel() {
  }

  public boolean isAccountLocked() {
    return accountLocked;
  }

  public LocalDateTime getDisabledTime() {
    return disabledTime;
  }

  public Integer getTimesLocked() {
    return timesLocked;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getRegistrationDate() {
    return registrationDate;
  }

  public LocalDateTime getLastModifiedTime() {
    return lastModifiedTime;
  }

  public Set<UserRoleEnum> getRoles() {
    return roles;
  }

  public UserNotificationViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserNotificationViewModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserNotificationViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserNotificationViewModel setRegistrationDate(LocalDateTime registrationDate) {
    this.registrationDate = registrationDate;
    return this;
  }

  public UserNotificationViewModel setLastModifiedTime(LocalDateTime lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
    return this;
  }

  public UserNotificationViewModel setEnabled(boolean enabled) {
    isEnabled = enabled;
    return this;
  }

  public UserNotificationViewModel setRoles(Set<UserRoleEnum> roles) {
    this.roles = roles;
    return this;
  }

  public UserNotificationViewModel setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
    return this;
  }

  public UserNotificationViewModel setDisabledTime(LocalDateTime disabledTime) {
    this.disabledTime = disabledTime;
    return this;
  }

  public UserNotificationViewModel setTimesLocked(Integer timesLocked) {
    this.timesLocked = timesLocked;
    return this;
  }
}
