package com.example.pathfinder.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {
  private boolean approved;
  private LocalDateTime created;
  private String textContent;
  private UserEntity author;
  private Route route;
  private Comment parentCommentId;
  private List<Comment> replies;

  public Comment() {
  }

  @ManyToOne
  @JoinColumn(name = "parent_comment_id")
  public Comment getParentCommentId() {
    return parentCommentId;
  }

  @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
  public List<Comment> getReplies() {
    return replies;
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
  public UserEntity getAuthor() {
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

  public void setAuthor(UserEntity user) {
    this.author = user;
  }

  public void setRoute(Route route) {
    this.route = route;
  }

  public Comment setReplies(List<Comment> replies) {
    this.replies = replies;
    return this;
  }

  public Comment setParentCommentId(Comment parentCommentId) {
    this.parentCommentId = parentCommentId;
    return this;
  }

  @PrePersist
  private void setCreationDate() {
    this.created = LocalDateTime.now();
  }
}
