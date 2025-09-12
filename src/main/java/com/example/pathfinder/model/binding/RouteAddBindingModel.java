package com.example.pathfinder.model.binding;

import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.validation.route.UniqueRouteName;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public class RouteAddBindingModel {
  private String name;
  private String description;
  private MultipartFile gpxCoordinates;
  private LevelEnum level;
  private String videoUrl;
  private Set<CategoryEnum> categories;
  private Integer distance;
  private List<MultipartFile> pictures;

  public RouteAddBindingModel() {
  }

  public List<MultipartFile> getPictures() {
    return pictures;
  }

  @NotNull(message = "Please insert distance")
  @Positive(message = "Distance should be positive")
  public Integer getDistance() {
    return distance;
  }

  @UniqueRouteName
  @Size(min = 3, max = 20, message = "Name should be between 3 and 20 symbols")
  public String getName() {
    return name;
  }

  @Size(min = 3, message = "Description should beat least 5 symbols long")
  public String getDescription() {
    return description;
  }

  public MultipartFile getGpxCoordinates() {
    return gpxCoordinates;
  }

  @NotEmpty(message = "At least one category should be selected")
  public Set<CategoryEnum> getCategories() {
    return categories;
  }

  @NotNull
  public LevelEnum getLevel() {
    return level;
  }

  @Pattern(regexp = "^$|^[a-zA-Z0-9_-]{11}$", message = "YouTube video ID must be 11 characters")
  public String getVideoUrl() {
    return videoUrl;
  }

  public RouteAddBindingModel setName(String name) {
    this.name = name;
    return this;
  }

  public RouteAddBindingModel setLevel(LevelEnum level) {
    this.level = level;
    return this;
  }

  public RouteAddBindingModel setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
    return this;
  }

  public RouteAddBindingModel setCategories(Set<CategoryEnum> categories) {
    this.categories = categories;
    return this;
  }

  public RouteAddBindingModel setGpxCoordinates(MultipartFile gpxCoordinates) {
    this.gpxCoordinates = gpxCoordinates;
    return this;
  }

  public RouteAddBindingModel setPictures(List<MultipartFile> pictures) {
    this.pictures = pictures;
    return this;
  }

  public RouteAddBindingModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public RouteAddBindingModel setDistance(Integer distance) {
    this.distance = distance;
    return this;
  }
}
