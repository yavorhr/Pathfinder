package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;

import java.util.Set;

public class RouteDetailsServiceModel {
  private Long id;
  private Long authorId;
  private String videoUrl;
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private Integer distance;
  private Set<Picture> pictures;
  private String authorFullName;
  private boolean canModify;
  private Set<CategoryEnum> categories;

  public RouteDetailsServiceModel() {
  }

  public Set<CategoryEnum> getCategories() {
    return categories;
  }

  public Long getId() {
    return id;
  }

  public Long getAuthorId() {
    return authorId;
  }

  public String getDescription() {
    return description;
  }

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

  public Integer getDistance() {
    return distance;
  }

  public Set<Picture> getPictures() {
    return pictures;
  }

  public String getAuthorFullName() {
    return authorFullName;
  }

  public boolean isCanModify() {
    return canModify;
  }

  public RouteDetailsServiceModel setId(Long id) {
    this.id = id;
    return this;
  }

  public RouteDetailsServiceModel setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public RouteDetailsServiceModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public RouteDetailsServiceModel setGpxCoordinates(String gpxCoordinates) {
    this.gpxCoordinates = gpxCoordinates;
    return this;
  }

  public RouteDetailsServiceModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }

  public RouteDetailsServiceModel setName(String name) {
    this.name = name;
    return this;
  }

  public RouteDetailsServiceModel setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public RouteDetailsServiceModel setDistance(Integer distance) {
    this.distance = distance;
    return this;
  }

  public RouteDetailsServiceModel setCategories(Set<CategoryEnum> categories) {
    this.categories = categories;
    return this;
  }

  public RouteDetailsServiceModel setPictures(Set<Picture> pictures) {
    this.pictures = pictures;
    return this;
  }

  public RouteDetailsServiceModel setAuthorFullName(String authorFullName) {
    this.authorFullName = authorFullName;
    return this;
  }

  public RouteDetailsServiceModel setCanModify(boolean canModify) {
    this.canModify = canModify;
    return this;
  }
}
