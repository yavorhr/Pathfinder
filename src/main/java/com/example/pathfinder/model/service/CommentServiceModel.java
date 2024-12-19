package com.example.pathfinder.model.service;

public class CommentServiceModel {
  private Long routeId;
  private String message;
  private String creatorEmail;

  public CommentServiceModel() {
  }

  public Long getRouteId() {
    return routeId;
  }

  public String getMessage() {
    return message;
  }

  public String getCreatorEmail() {
    return creatorEmail;
  }

  public CommentServiceModel setRouteId(Long routeId) {
    this.routeId = routeId;
    return this;
  }

  public CommentServiceModel setMessage(String message) {
    this.message = message;
    return this;
  }

  public CommentServiceModel setCreatorEmail(String creatorEmail) {
    this.creatorEmail = creatorEmail;
    return this;
  }
}
