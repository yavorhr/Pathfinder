package com.example.pathfinder.model.binding;

public class RoleUpdateRequest {
  private String username;
  private String[] roles;

  public RoleUpdateRequest() {
  }

  public String getUsername() {
    return username;
  }

  public String[] getRoles() {
    return roles;
  }

  public RoleUpdateRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public RoleUpdateRequest setRoles(String[] roles) {
    this.roles = roles;
    return this;
  }
}
