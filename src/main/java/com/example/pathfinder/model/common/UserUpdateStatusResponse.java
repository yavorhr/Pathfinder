package com.example.pathfinder.model.common;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import java.time.LocalDateTime;
import java.util.Set;

public class UserUpdateStatusResponse {
  private String email;
  private boolean isEnabled;
  private Set<UserRoleEnum> roles;
  private LocalDateTime disabledTime;
  private boolean accountLocked;

  public UserUpdateStatusResponse() {
  }

  public LocalDateTime getDisabledTime() {
    return disabledTime;
  }

  public boolean isAccountLocked() {
    return accountLocked;
  }

  public String getEmail() {
    return email;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public Set<UserRoleEnum> getRoles() {
    return roles;
  }

  public UserUpdateStatusResponse setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserUpdateStatusResponse setEnabled(boolean enabled) {
    isEnabled = enabled;
    return this;
  }

  public UserUpdateStatusResponse setRoles(Set<UserRoleEnum> roles) {
    this.roles = roles;
    return this;
  }

  public UserUpdateStatusResponse setDisabledTime(LocalDateTime disabledTime) {
    this.disabledTime = disabledTime;
    return this;
  }

  public UserUpdateStatusResponse setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
    return this;
  }
}
