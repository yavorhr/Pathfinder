package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.Lob;

import java.util.Set;

public class AddRouteServiceModel {
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private String videoUrl;
  private User author;
  private Set<CategoryEnum> categories;

  public AddRouteServiceModel() {
  }

  public String getDescription() {
    return description;
  }

  @Lob
  public String getGpxCoordinates() {
    return gpxCoordinates;
  }

  public LevelEnum getLevel() {
    return level;
  }

  public String getName() {
    return name;
  }

  public String getVideoUrl() {
    return videoUrl;
  }

  public User getAuthor() {
    return author;
  }

  public Set<CategoryEnum> getCategories() {
    return categories;
  }

  public AddRouteServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public AddRouteServiceModel setGpxCoordinates(String gpxCoordinates) {
    this.gpxCoordinates = gpxCoordinates;
    return this;
  }

  public AddRouteServiceModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }

  public AddRouteServiceModel setName(String name) {
    this.name = name;
    return this;
  }

  public AddRouteServiceModel setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public AddRouteServiceModel setAuthor(User author) {
    this.author = author;
    return this;
  }

  public AddRouteServiceModel setCategories(Set<CategoryEnum> categories) {
    this.categories = categories;
    return this;
  }
}
