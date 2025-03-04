package com.example.pathfinder.model.binding;

import com.example.pathfinder.validation.register.DoesPasswordAndConfirmPasswordMatch;
import com.example.pathfinder.validation.register.UniqueEmail;
import com.example.pathfinder.validation.register.UniqueUsername;
import jakarta.validation.constraints.*;

@DoesPasswordAndConfirmPasswordMatch
public class UserRegisterBindingModel {
  private String username;
  private String firstName;
  private String lastName;
  private String gender;
  private String year;
  private String month;
  private String day;
  private String email;
  private String password;
  private String confirmPassword;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;

  public UserRegisterBindingModel() {
  }

  @UniqueUsername
  @NotNull
  @Size(min = 4, max = 20, message = "Username must be between 4 and 20 symbols")
  public String getUsername() {
    return username;
  }

  @NotNull
  @Size(min = 3, max = 20, message = "Fist name must be between 3 and 20 symbols")
  public String getFirstName() {
    return this.firstName;
  }

  @NotNull
  @Size(min = 3, max = 20, message = "Last name must be between 3 and 20 symbols")
  public String getLastName() {
    return this.lastName;
  }

  @NotNull(message = "Please select one of the options")
  public String getGender() {
    return gender;
  }

  @UniqueEmail
  @Email(message = "Please insert valid email")
  public String getEmail() {
    return email;
  }

  @NotNull
  @Size(min = 5, max = 20)
  public String getPassword() {
    return password;
  }

  @NotNull
  @Size(min = 5, max = 20)
  public String getConfirmPassword() {
    return confirmPassword;
  }

  public UserRegisterBindingModel setUsername(String username) {
    this.username = username;
    return this;
  }

  @NotNull(message = "Please select birthday")
  public String getYear() {
    return year;
  }

  @NotNull(message = "Please select birthday")
  public String getMonth() {
    return month;
  }

  @NotNull(message = "Please select birthday")
  public String getDay() {
    return day;
  }

  public UserRegisterBindingModel setGender(String gender) {
    this.gender = gender;
    return this;
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

  public UserRegisterBindingModel setEmail(String email) {
    this.email = email;
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

  public UserRegisterBindingModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public UserRegisterBindingModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public UserRegisterBindingModel setInstagramAcc(String instagramAcc) {
    this.instagramAcc = instagramAcc;
    return this;
  }

  public UserRegisterBindingModel setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
    return this;
  }

  public UserRegisterBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserRegisterBindingModel setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserRegisterBindingModel setYear(String year) {
    this.year = year;
    return this;
  }

  public UserRegisterBindingModel setMonth(String month) {
    this.month = month;
    return this;
  }

  public UserRegisterBindingModel setDay(String day) {
    this.day = day;
    return this;
  }
}
