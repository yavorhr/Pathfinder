package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
  private String firstName;
  private String lastName;
  private GenderEnum gender;
  private String email;
  private String username;
  private LocalDate birthday;
  private LevelEnum level;
  private String password;
  private String aboutMe;
  private Set<UserRoleEntity> roles;
  private String facebookAcc;
  private String instagramAcc;
  private String linkedIn;
  private boolean enabled;
  private List<Route> routes;
  private LocalDateTime registrationDate;
  private LocalDateTime lastModifiedTime;
  private List<Comment> comments;
  private String profileImageUrl;
  private String profileImagePublicId;
  private boolean accountLocked;
  private Integer failedLoginAttempts;
  private LocalDateTime lastFailedLogin;
  private LocalDateTime lockTime;
  private Integer lockedAccountCounter;

  public UserEntity() {
    this.enabled = false;
    this.accountLocked = false;
    this.failedLoginAttempts = 0;
    this.lockedAccountCounter = 0;
  }

  @Column(name = "is_account_locked")
  public boolean isAccountLocked() {
    return accountLocked;
  }

  @Column(name = "locked_account_counter")
  public Integer getLockedAccountCounter() {
    return lockedAccountCounter;
  }

  @Column(name = "failed_login_attempts")
  public Integer getFailedLoginAttempts() {
    return failedLoginAttempts;
  }

  @Column(name = "last_failed_login")
  public LocalDateTime getLastFailedLogin() {
    return lastFailedLogin;
  }

  @Column(name = "lock_time")
  public LocalDateTime getLockTime() {
    return lockTime;
  }

  @Column(name = "profile_image_url")
  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  @Column(name = "profile_image_public_id")
  public String getProfileImagePublicId() {
    return profileImagePublicId;
  }

  @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
  public List<Comment> getComments() {
    return comments;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "gender", nullable = false)
  public GenderEnum getGender() {
    return gender;
  }

  @NotNull
  public LocalDate getBirthday() {
    return birthday;
  }

  @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
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

  public UserEntity setLockedAccountCounter(int lockedAccountCounts) {
    this.lockedAccountCounter = lockedAccountCounts;
    return this;
  }

  @Column(unique = true, nullable = false)
  public String getUsername() {
    return username;
  }

  @Column(nullable = false, name = "isEnabled")
  public boolean isEnabled() {
    return enabled;
  }

  @Column(name = "last_modified_time")
  public LocalDateTime getLastModifiedTime() {
    return lastModifiedTime;
  }

  public UserEntity setProfileImageUrl(String url) {
    this.profileImageUrl = url;
    return this;
  }

  public UserEntity setAccountLocked(boolean accountLocked) {
    this.accountLocked = accountLocked;
    return this;
  }

  public UserEntity setFailedLoginAttempts(int failedLoginAttempts) {
    this.failedLoginAttempts = failedLoginAttempts;
    return this;
  }

  public UserEntity setLastFailedLogin(LocalDateTime lastFailedLogin) {
    this.lastFailedLogin = lastFailedLogin;
    return this;
  }

  public UserEntity setLockTime(LocalDateTime lockTime) {
    this.lockTime = lockTime;
    return this;
  }

  public UserEntity setProfileImagePublicId(String publicId) {
    this.profileImagePublicId = publicId;
    return this;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  public Set<UserRoleEntity> getRoles() {
    return roles;
  }

  @Column(name = "registration_date", nullable = false)
  public LocalDateTime getRegistrationDate() {
    return registrationDate;
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

  public UserEntity setComments(List<Comment> comments) {
    this.comments = comments;
    return this;
  }

  public UserEntity setGender(GenderEnum gender) {
    this.gender = gender;
    return this;
  }

  public UserEntity setBirthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

  public UserEntity setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
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

  public UserEntity setRegistrationDate(LocalDateTime registeredOn) {
    this.registrationDate = registeredOn;
    return this;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserEntity setUsername(String username) {
    this.username = username;
    return this;
  }

  public void setRoles(Set<UserRoleEntity> roles) {
    this.roles = roles;
  }

  public UserEntity setEmail(String email) {
    this.email = email;
    return this;
  }

  public UserEntity setLastModifiedTime(LocalDateTime lastModified) {
    this.lastModifiedTime = lastModified;
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

  @PrePersist
  private void setInitialValues() {
    this.registrationDate = LocalDateTime.now();
    this.profileImagePublicId = "pathfinder/users-pictures/xv9rvzhomv0a1yojubw3";
    this.profileImageUrl = "https://res.cloudinary.com/yavorhr/image/upload/v1738929875/pathfinder/users-pictures/profile_m3npdq.jpg";
  }

  @PreUpdate
  private void setUpdateDate() {
    this.lastModifiedTime = LocalDateTime.now();
  }
}
