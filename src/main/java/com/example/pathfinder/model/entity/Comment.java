package com.example.pathfinder.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
  private boolean approved;
  private LocalDateTime created;
  private String textContent;
  private User author;
  private Route route;

  public Comment() {
  }

  @Column(nullable = false)
  public boolean isApproved() {
    return approved;
  }

  @Column(nullable = false)
  public LocalDateTime getCreated() {
    return created;
  }

  @Column(nullable = false, columnDefinition = "LONGTEXT")
  public String getTextContent() {
    return textContent;
  }

  @ManyToOne
  public User getAuthor() {
    return author;
  }

  @ManyToOne
  public Route getRoute() {
    return route;
  }

  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public void setTextContent(String textContent) {
    this.textContent = textContent;
  }

  public void setAuthor(User user) {
    this.author = user;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  @PrePersist
  private void setCreationDate() {
    this.created = LocalDateTime.now();
  }
}
