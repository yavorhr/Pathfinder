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

  @Column(name = "public_id_number",  columnDefinition = "VARCHAR(255) DEFAULT 'pathfinder/users-pictures/xv9rvzhomv0a1yojubw3'")
  public String getPublicId() {
    return publicId;
  }

  @Column(name = "url",  columnDefinition = "VARCHAR(255) DEFAULT 'https://res.cloudinary.com/yavorhr/image/upload/v1738929875/pathfinder/users-pictures/profile_m3npdq.jpg'")
  public String getUrl() {
    return url;
  }

  @OneToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
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
}
