package com.example.pathfinder.model.binding;

import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.validation.route.UniqueRouteName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;

public class RouteAddBindingModel {
  private String name;
  private String description;
  private MultipartFile gpxCoordinates;
  private LevelEnum level;
  private String videoUrl;
  private Set<CategoryEnum> categories;
  private Integer distance;

  public RouteAddBindingModel() {
  }

  @NotNull
  @Positive
  public Integer getDistance() {
    return distance;
  }

  @UniqueRouteName
  @Size(min = 3, max = 20, message = "Name must be between 3 and 20 charters")
  public String getName() {
    return name;
  }

  @Size(min = 3)
  public String getDescription() {
    return description;
  }

  public MultipartFile getGpxCoordinates() {
    return gpxCoordinates;
  }

  public Set<CategoryEnum> getCategories() {
    return categories;
  }

  @NotNull
  public LevelEnum getLevel() {
    return level;
  }

  @Size(min = 11, max = 11)
  @NotNull
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

  public RouteAddBindingModel setDescription(String description) {
    this.description = description;
    return this;
  }

  public RouteAddBindingModel setDistance(Integer distance) {
    this.distance = distance;
    return this;
  }
}
