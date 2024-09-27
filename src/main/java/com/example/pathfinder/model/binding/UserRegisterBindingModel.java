package com.example.pathfinder.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserRegisterBindingModel {
  private String username;
  private String fullName;
  private String email;
  private Integer age;
  private String password;
  private String confirmPassword;

  public UserRegisterBindingModel() {
  }

  @NotNull
  @Size(min = 5,max = 20)
  public String getUsername() {
    return username;
  }

//  @NotNull
//  @Size(min = 3, max = 20)
  public String getFullName() {
    return fullName;
  }

  @Email
  @NotNull
  public String getEmail() {
    return email;
  }

//  @Positive
  public Integer getAge() {
    return age;
  }

//  @NotNull
//  @Size(min = 5,max = 20)
  public String getPassword() {
    return password;
  }

//  @NotNull
//  @Size(min = 5,max = 20)
  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserRegisterBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserRegisterBindingModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public UserRegisterBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserRegisterBindingModel setAge(Integer age) {
    this.age = age;
    return this;
  }

  public UserRegisterBindingModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }
}
