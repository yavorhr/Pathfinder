package com.example.pathfinder.model.binding;

import com.example.pathfinder.validation.login.ValidLogin;

@ValidLogin
public class UserLoginBindingModel {
  private String username;
  private String password;

  public UserLoginBindingModel() {
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public UserLoginBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserLoginBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }
}
