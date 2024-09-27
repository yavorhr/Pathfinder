package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.enums.LevelEnum;

public class UserProfileServiceModel {
  private Long id;
  private String fullName;
  private Integer age;
  private String username;
  private LevelEnum level;

  public UserProfileServiceModel() {
  }

  public Long getId() {
    return id;
  }

  public String getFullName() {
    return fullName;
  }

  public Integer getAge() {
    return age;
  }

  public String getUsername() {
    return username;
  }

  public LevelEnum getLevel() {
    return level;
  }

  public UserProfileServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserProfileServiceModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public UserProfileServiceModel setAge(Integer age) {
    this.age = age;
    return this;
  }

  public UserProfileServiceModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserProfileServiceModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }
}
