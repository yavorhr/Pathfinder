package com.example.pathfinder.model.service;

public class CommentServiceModel {
  private boolean approved;
  private String textContent;
  private Long authorId;
  private Long routeId;

  public CommentServiceModel() {
  }

  public boolean isApproved() {
    return approved;
  }

  public CommentServiceModel setApproved(boolean approved) {
    this.approved = approved;
    return this;
  }

  public String getTextContent() {
    return textContent;
  }

  public CommentServiceModel setTextContent(String textContent) {
    this.textContent = textContent;
    return this;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public CommentServiceModel setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public Long getRouteId() {
    return routeId;
  }

  public CommentServiceModel setRouteId(Long routeId) {
    this.routeId = routeId;
    return this;
  }
}
