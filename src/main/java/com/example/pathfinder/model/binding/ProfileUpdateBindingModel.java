package com.example.pathfinder.model.binding;

import com.example.pathfinder.validation.register.UniqueEmail;
import com.example.pathfinder.validation.register.UniqueUsername;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProfileUpdateBindingModel {
  private Long id;
  private Integer age;
  private String fullName;
  private String email;
  private String username;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;

  public ProfileUpdateBindingModel() {
  }

  @UniqueUsername
  @Size(min = 4,max = 20, message = "Username must be between 4 and 20 symbols")
  public String getUsername() {
    return username;
  }

  public Long getId() {
    return id;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  @Positive(message = "Age must be positive")
  public Integer getAge() {
    return age;
  }

  @Size(min = 3, max = 30, message = "Full name must be between 3 and 30 symbols")
  public String getFullName() {
    return fullName;
  }

  @NotNull(message = "Email is required")
  @UniqueEmail
  public String getEmail() {
    return email;
  }

  public ProfileUpdateBindingModel setId(Long id) {
    this.id = id;
    return this;
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

  public ProfileUpdateBindingModel setAge(Integer age) {
    this.age = age;
    return this;
  }

  public ProfileUpdateBindingModel setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public ProfileUpdateBindingModel setEmail(String email) {
    this.email = email;
    return this;
  }

  public ProfileUpdateBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  public ProfileUpdateBindingModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public ProfileUpdateBindingModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public ProfileUpdateBindingModel setInstagramAcc(String instagramAcc) {
    this.instagramAcc = instagramAcc;
    return this;
  }

  public ProfileUpdateBindingModel setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
    return this;
  }
}
