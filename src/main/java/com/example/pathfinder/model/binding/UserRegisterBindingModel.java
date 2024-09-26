package com.example.pathfinder.model.binding;

public class UserRegisterBindingModel {
  private String username;
  private String fullName;
  private String email;
  private Integer age;
  private String password;
  private String confirmPassword;

  public UserRegisterBindingModel() {
  }

  public String getUsername() {
    return username;
  }

  public String getFullName() {
    return fullName;
  }

  public String getEmail() {
    return email;
  }

  public Integer getAge() {
    return age;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserRegisterBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }
}
