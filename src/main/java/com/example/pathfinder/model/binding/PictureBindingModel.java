package com.example.pathfinder.model.binding;

import org.springframework.web.multipart.MultipartFile;

public class PictureBindingModel {

  private String title;
  private MultipartFile picture;

  public String getTitle() {
    return title;
  }

  public PictureBindingModel setTitle(String title) {
    this.title = title;
    return this;
  }

  public MultipartFile getPicture() {
    return picture;
  }

  public PictureBindingModel setPicture(MultipartFile picture) {
    this.picture = picture;
    return this;
  }
}
