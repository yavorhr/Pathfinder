package com.example.pathfinder.model.common;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;

import java.util.Set;

public class UserUpdateStatusResponse {
  private String username;
  private boolean isEnabled;
  private Set<UserRoleEnum> roles;

  public UserUpdateStatusResponse() {
  }

  public String getUsername() {
    return username;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public Set<UserRoleEnum> getRoles() {
    return roles;
  }

  public UserUpdateStatusResponse setUsername(String username) {
    this.username = username;
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
}
