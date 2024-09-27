package com.example.pathfinder.model.service;

public class UserLoginServiceModel {
  private Long id;
  private String username;

  public UserLoginServiceModel() {
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public UserLoginServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserLoginServiceModel setUsername(String username) {
    this.username = username;
    return this;
  }
}
