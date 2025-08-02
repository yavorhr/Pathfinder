package com.example.pathfinder.model.view;

public class CommentViewModel {
  private Long commentId;
  private String textContent;
  private String author;
  private String created;
  private boolean approved;
  private boolean canModify;

  public CommentViewModel() {

  }

  public CommentViewModel(Long commentId, String textContent, String author) {
    this.commentId = commentId;
    this.textContent = textContent;
    this.author = author;
  }

  public boolean isCanModify() {
    return canModify;
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

  public String getCreated() {
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

  public CommentViewModel setCreated(String created) {
    this.created = created;
    return this;
  }

  public CommentViewModel setApproved(boolean approved) {
    this.approved = approved;
    return this;
  }

  public CommentViewModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }
}
