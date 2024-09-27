package com.example.pathfinder.model.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class UserRegisterServiceModel {
  private String username;
  private String email;
  private String fullName;
  private Integer age;
  private String password;

  public UserRegisterServiceModel() {
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return fullName;
  }

  public Integer getAge() {
    return age;
  }

  public String getPassword() {
    return password;
  }

  public UserRegisterServiceModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserRegisterServiceModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserRegisterServiceModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public UserRegisterServiceModel setAge(Integer age) {
    this.age = age;
    return this;
  }

  public UserRegisterServiceModel setPassword(String password) {
    this.password = password;
    return this;
  }
}
