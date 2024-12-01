package com.example.pathfinder.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class PictureAddBindingModel {

  private String title;
  private MultipartFile picture;
  private Long routeId;

  public String getTitle() {
    return title;
  }

  public PictureAddBindingModel setTitle(String title) {
    this.title = title;
    return this;
  }

  public Long getRouteId() {
    return routeId;
  }
  public MultipartFile getPicture() {
    return picture;
  }

  public PictureAddBindingModel setPicture(MultipartFile picture) {
    this.picture = picture;
    return this;
  }

  public PictureAddBindingModel setRouteId(Long routeId) {
    this.routeId = routeId;
    return this;
  }
}
