package com.example.pathfinder.model.service;

public class UserRegisterServiceModel {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private Integer age;
  private String password;

  public UserRegisterServiceModel() {
  }

  public String getGender() {
    return gender;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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

  public UserRegisterServiceModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserRegisterServiceModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserRegisterServiceModel setGender(String gender) {
    this.gender = gender;
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
