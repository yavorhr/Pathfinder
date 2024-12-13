package com.example.pathfinder.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pictures")
public class Picture extends BaseEntity {
  private String title;
  private String url;
  private UserEntity author;
  private Route route;
  private String publicId;

  public Picture() {
  }

  public String getPublicId() {
    return publicId;
  }

  @ManyToOne
  public UserEntity getAuthor() {
    return author;
  }

  @ManyToOne
  public Route getRoute() {
    return route;
  }

  @Column()
  public String getTitle() {
    return title;
  }

  @Column()
  public String getUrl() {
    return url;
  }

  public Picture setTitle(String title) {
    this.title = title;
    return this;
  }

  public Picture setUrl(String url) {
    this.url = url;
    return this;
  }

  public Picture setAuthor(UserEntity author) {
    this.author = author;
    return this;
  }

  public Picture setRoute(Route route) {
    this.route = route;
    return this;
  }

  public Picture setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }
}
