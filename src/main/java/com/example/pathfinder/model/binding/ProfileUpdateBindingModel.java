package com.example.pathfinder.model.binding;
import jakarta.validation.constraints.Size;

public class ProfileUpdateBindingModel {
  private Long id;
  private String firstName;
  private String lastName;
  private String aboutMe;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;

  public ProfileUpdateBindingModel() {
  }

  public Long getId() {
    return id;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  @Size(min = 3, max = 30, message = "Length be between 2 and 20 symbols")
  public String getFirstName() {
    return firstName;
  }

  @Size(min = 3, max = 30, message = "Length be between 2 and 20 symbols")
  public String getLastName() {
    return lastName;
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

  public ProfileUpdateBindingModel setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public ProfileUpdateBindingModel setFacebookAcc(String facebookAcc) {
    this.facebookAcc = facebookAcc;
    return this;
  }

  public ProfileUpdateBindingModel setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public ProfileUpdateBindingModel setLastName(String lastName) {
    this.lastName = lastName;
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
