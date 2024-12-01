package com.example.pathfinder.model.service;

public class PictureAddServiceModel {
  private String title;
  private Long routeId;
  private Long authorId;
  private String publicId;
  private String url;

  public Long getAuthorId() {
    return authorId;
  }

  public String getPublicId() {
    return publicId;
  }

  public String getTitle() {
    return title;
  }

  public PictureAddServiceModel setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Long getRouteId() {
    return routeId;
  }

  public PictureAddServiceModel setRouteId(Long routeId) {
    this.routeId = routeId;
    return this;
  }

  public PictureAddServiceModel setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public PictureAddServiceModel setPublicId(String publicId) {
    this.publicId = publicId;
    return this;
  }

  public PictureAddServiceModel setUrl(String url) {
    this.url = url;
    return this;
  }
}
