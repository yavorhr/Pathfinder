package com.example.pathfinder.model.view;

public class RouteViewModel {
  private Long id;
  private String name;
  private String description;
  private String pictureUrl;

  public RouteViewModel() {
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getPictureUrl() {
    return pictureUrl;
  }

  public RouteViewModel setId(Long id) {
    this.id = id;
    return this;
  }

  public RouteViewModel setName(String name) {
    this.name = name;
    return this;
  }

  public RouteViewModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public RouteViewModel setPictureUrl(String pictureUrl) {
    this.pictureUrl = pictureUrl;
    return this;
  }
}
