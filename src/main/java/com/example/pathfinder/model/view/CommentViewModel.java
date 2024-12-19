package com.example.pathfinder.model.view;

import java.time.LocalDateTime;

public class CommentViewModel {
  private Long commentId;
  private String textContent;
  private String author;
  private LocalDateTime created;
  private boolean approved;

  public CommentViewModel() {
  }

  public Long getCommentId() {
    return commentId;
  }

  public String getTextContent() {
    return textContent;
  }

  public String getAuthor() {
    return author;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public boolean isApproved() {
    return approved;
  }


  public CommentViewModel setCommentId(Long commentId) {
    this.commentId = commentId;
    return this;
  }

  public CommentViewModel setTextContent(String textContent) {
    this.textContent = textContent;
    return this;
  }

  public CommentViewModel setAuthor(String author) {
    this.author = author;
    return this;
  }

  public CommentViewModel setCreated(LocalDateTime created) {
    this.created = created;
    return this;
  }

  public CommentViewModel setApproved(boolean approved) {
    this.approved = approved;
    return this;
  }

}
