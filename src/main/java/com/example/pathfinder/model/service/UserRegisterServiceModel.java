package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.enums.GenderEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class UserRegisterServiceModel {
  private String username;
  private String email;
  private String firstName;
  private String lastName;
  private LocalDate birthday;
  private GenderEnum gender;
  private String password;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;

  public UserRegisterServiceModel() {
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  @Enumerated(EnumType.STRING)
  public GenderEnum getGender() {
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

  public String getPassword() {
    return password;
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

  public UserRegisterServiceModel setGender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  public UserRegisterServiceModel setPassword(String password) {
    this.password = password;
    return this;
  }

  public UserRegisterServiceModel setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  public UserRegisterServiceModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public UserRegisterServiceModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public UserRegisterServiceModel setInstagramAcc(String instagramAcc) {
    this.instagramAcc = instagramAcc;
    return this;
  }

  public UserRegisterServiceModel setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
    return this;
  }
}
