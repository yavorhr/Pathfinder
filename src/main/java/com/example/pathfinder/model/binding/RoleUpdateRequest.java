package com.example.pathfinder.model.binding;

public class RoleUpdateRequest {
  private String email;
  private String[] roles;

  public RoleUpdateRequest() {
  }

  public String getEmail() {
    return email;
  }

  public String[] getRoles() {
    return roles;
  }

  public RoleUpdateRequest setEmail(String email) {
    this.email = email;
    return this;
  }

  public RoleUpdateRequest setRoles(String[] roles) {
    this.roles = roles;
    return this;
  }
}
