package com.example.pathfinder.model.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class UserLoginBindingModel {
  private String password;
  private String username;

  public UserLoginBindingModel() {
  }

  @NotNull
  @NotEmpty
  public String getPassword() {
    return password;
  }

  @NotNull
  @NotEmpty
  public String getUsername() {
    return username;
  }

  public UserLoginBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserLoginBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }
}
