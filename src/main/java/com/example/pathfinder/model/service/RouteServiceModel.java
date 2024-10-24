package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.Lob;

import java.util.HashSet;
import java.util.Set;

public class RouteServiceModel {
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private String videoUrl;
  private User author;
  private Set<Category> categories;
  private Set<Picture> pictures;

  public RouteServiceModel() {
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

  public Set<Category> getCategories() {
    return categories;
  }

  public Set<Picture> getPictures() {
    return pictures;
  }

  public RouteServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public RouteServiceModel setGpxCoordinates(String gpxCoordinates) {
    this.gpxCoordinates = gpxCoordinates;
    return this;
  }

  public RouteServiceModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }

  public RouteServiceModel setName(String name) {
    this.name = name;
    return this;
  }

  public RouteServiceModel setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public RouteServiceModel setAuthor(User author) {
    this.author = author;
    return this;
  }

  public RouteServiceModel setCategories(Set<Category> categories) {
    this.categories = categories;
    return this;
  }

  public RouteServiceModel setPictures(Set<Picture> pictures) {
    this.pictures = pictures;
    return this;
  }
}
