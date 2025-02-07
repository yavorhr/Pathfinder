package com.example.pathfinder.model.view;

import com.example.pathfinder.model.entity.enums.LevelEnum;

public class UserProfileViewModel {
  private Long id;
  private String fullName;
  private Integer age;
  private String username;
  private LevelEnum level;
  private String email;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;
  private String profileImage;

  public UserProfileViewModel() {
  }

  public String getProfileImage() {
    return profileImage;
  }

  public String getEmail() {
    return email;
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

  public String getAboutMe() {
    return aboutMe;
  }

  public String getFacebookAcc() {
    return facebookAcc;
  }

  public String getInstagramAcc() {
    return instagramAcc;
  }

  public String getLinkedIn() {
    return linkedIn;
  }

  public UserProfileViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserProfileViewModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public UserProfileViewModel setProfileImage(String profileImage) {
    this.profileImage = profileImage;
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

  public UserProfileViewModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserProfileViewModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public UserProfileViewModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public UserProfileViewModel setInstagramAcc(String instagramAcc) {
    this.instagramAcc = instagramAcc;
    return this;
  }

  public UserProfileViewModel setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
    return this;
  }
}
