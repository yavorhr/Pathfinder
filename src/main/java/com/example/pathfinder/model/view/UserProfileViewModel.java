package com.example.pathfinder.model.view;

import com.example.pathfinder.model.entity.enums.LevelEnum;

public class UserProfileViewModel {
  private Long id;
  private String fullName;
  private Integer age;
  private String username;
  private LevelEnum level;

  public UserProfileViewModel() {
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

  public UserProfileViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserProfileViewModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public UserProfileViewModel setAge(Integer age) {
    this.age = age;
    return this;
  }

  public UserProfileViewModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserProfileViewModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }
}
