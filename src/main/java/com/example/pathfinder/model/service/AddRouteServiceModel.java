package com.example.pathfinder.model.service;

import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.Lob;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class AddRouteServiceModel {
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private String videoUrl;
  private Long authorId;
  private Set<CategoryEnum> categories;
  private Integer distance;
  private List<MultipartFile> pictures;

  public AddRouteServiceModel() {
  }

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  public Integer getDistance() {
    return distance;
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

  public Long getAuthorId() {
    return authorId;
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

  public AddRouteServiceModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public AddRouteServiceModel setAuthorId(Long authorId) {
    this.authorId = authorId;
    return this;
  }

  public AddRouteServiceModel setCategories(Set<CategoryEnum> categories) {
    this.categories = categories;
    return this;
  }

  public AddRouteServiceModel setDistance(Integer distance) {
    this.distance = distance;
    return this;
  }
}
