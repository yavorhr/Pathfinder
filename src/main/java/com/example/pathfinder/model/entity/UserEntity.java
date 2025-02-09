package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
  private String firstName;
  private String lastName;
  private String gender;
  private String email;
  private String username;
  private Integer age;
  private LevelEnum level;
  private String password;
  private String aboutMe;
  private Set<UserRoleEntity> roles;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;
  private boolean enabled;
  private List<Route> routes;
  private ProfilePicture profilePicture;

  public UserEntity() {
    this.enabled = false;
  }

  @OneToOne
  public ProfilePicture getProfilePicture() {
    return profilePicture;
  }

  @Column(name = "gender", nullable = false)
  public String getGender() {
    return gender;
  }

  @OneToMany(mappedBy = "author")
  public List<Route> getRoutes() {
    return routes;
  }

  @Column(name = "first_name", nullable = false)
  public String getFirstName() {
    return firstName;
  }

  @Column(name = "last_name", nullable = false)
  public String getLastName() {
    return lastName;
  }

  @Lob
  @Column(name = "about_me")
  public String getAboutMe() {
    return aboutMe;
  }

  @Column
  public Integer getAge() {
    return age;
  }

  @Column(unique = true, nullable = false)
  public String getEmail() {
    return email;
  }

  @Enumerated(EnumType.STRING)
  public LevelEnum getLevel() {
    return level;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  @Column(unique = true, nullable = false)
  public String getUsername() {
    return username;
  }

  @Column(nullable = false, name = "enabled")
  public boolean isEnabled() {
    return enabled;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  public Set<UserRoleEntity> getRoles() {
    return roles;
  }

  @Column(name = "facebook_account")
  public String getFacebookAcc() {
    return facebookAcc;
  }

  @Column(name = "instagram_account")
  public String getInstagramAcc() {
    return instagramAcc;
  }

  @Column(name = "linked_in_account")
  public String getLinkedIn() {
    return linkedIn;
  }

  public UserEntity setGender(String gender) {
    this.gender = gender;
    return this;
  }

  public UserEntity setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
    return this;
  }

  public UserEntity setProfilePicture(ProfilePicture profilePicture) {
    this.profilePicture = profilePicture;
    return this;
  }

  public UserEntity setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserEntity setRoutes(List<Route> routes) {
    this.routes = routes;
    return this;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setRoles(Set<UserRoleEntity> roles) {
    this.roles = roles;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserEntity setEnabled(boolean enabled) {
    this.enabled = enabled;
    return this;
  }

  public UserEntity setFacebookAcc(String facebookLink) {
    this.facebookAcc = facebookLink;
    return this;
  }

  public UserEntity setInstagramAcc(String instagramLink) {
    this.instagramAcc = instagramLink;
    return this;
  }

  public UserEntity setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserEntity setLinkedIn(String linkedInLink) {
    this.linkedIn = linkedInLink;
    return this;
  }

}
