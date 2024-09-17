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
  private User author;
  private Route route;

  public Picture() {
  }

  @Column()
  public String getTitle() {
    return title;
  }

  @Column()
  public String getUrl() {
    return url;
  }

  @ManyToOne
  public User getAuthor() {
    return author;
  }

  @ManyToOne
  public Route getRoute() {
    return route;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setRoute(Route route) {
    this.route = route;
  }
}
