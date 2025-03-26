package com.example.pathfinder.model.view;

import com.example.pathfinder.model.entity.enums.LevelEnum;

import java.time.LocalDate;
import java.time.Period;

public class UserProfileViewModel {
  private Long id;
  private String firstName;
  private String lastName;
  private LocalDate birthday;
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

  public LocalDate getBirthday() {
    return birthday;
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

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
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

  public UserProfileViewModel setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    this.age = calculateAge(birthday);
    return this;
  }

  public UserProfileViewModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserProfileViewModel setLastName(String lastName) {
    this.lastName = lastName;
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

  private int calculateAge(LocalDate birthday) {
    return (birthday != null) ? Period.between(birthday, LocalDate.now()).getYears() : 0;
  }
}
