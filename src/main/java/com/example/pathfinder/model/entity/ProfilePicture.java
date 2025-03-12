package com.example.pathfinder.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "profile_pictures")
public class ProfilePicture extends BaseEntity {
  private String publicId;
  private String url;
  private UserEntity user;

  public ProfilePicture() {
  }

  @Column(name = "public_id_number")
  public String getPublicId() {
    return publicId;
  }

  @Column(name = "url")
  public String getUrl() {
    return url;
  }

  @OneToOne(mappedBy = "profilePicture", cascade = CascadeType.ALL)
  public UserEntity getUser() {
    return user;
  }

  public ProfilePicture setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }

  public ProfilePicture setUrl(String url) {
    this.url = url;
    return this;
  }

  public ProfilePicture setUser(UserEntity userEntity) {
    this.user = userEntity;
    return this;
  }

  @PrePersist
  public void setDefaultValues() {
    if (this.publicId == null || this.publicId.isEmpty()) {
      this.publicId = "pathfinder/users-pictures/xv9rvzhomv0a1yojubw3";
    }

    if (this.url == null || this.url.isEmpty()) {
      this.url = "https://res.cloudinary.com/yavorhr/image/upload/v1738929875/pathfinder/users-pictures/profile_m3npdq.jpg";
    }
  }
}
