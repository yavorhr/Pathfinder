package com.example.pathfinder.model.service;

public class UserProfileServiceModel {
  private Long id;
  private String firstName;
  private String lastName;
  private String username;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;

  public UserProfileServiceModel() {
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public UserProfileServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public UserProfileServiceModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public UserProfileServiceModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserProfileServiceModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserProfileServiceModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public UserProfileServiceModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public UserProfileServiceModel setInstagramAcc(String instagramAcc) {
    this.instagramAcc = instagramAcc;
    return this;
  }

  public UserProfileServiceModel setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
    return this;
  }
}
