package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private String videoUrl;
  private User author;
  private Set<Category> categories;

  public Route() {
  }

  @ManyToMany
  @JoinTable(
          name = "routes_categories", // The name of the join table
          joinColumns = @JoinColumn(name = "route_id"), // The join column in the join table that refers to this entity (Role)
          inverseJoinColumns = @JoinColumn(name = "category_id") // The join column that refers to the other entity (User)
  )
  public Set<Category> getCategories() {
    return categories;
  }

  @ManyToOne
  public User getAuthor() {
    return author;
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  @Column(name = "gpx_coordinates", columnDefinition = "LONGTEXT")
  public String getGpxCoordinates() {
    return gpxCoordinates;
  }

  @Enumerated(EnumType.STRING)
  public LevelEnum getLevel() {
    return level;
  }

  @Column(nullable = false,unique = true)
  public String getName() {
    return name;
  }

  @Column(name = "video_url")
  public String getVideoUrl() {
    return videoUrl;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setGpxCoordinates(String gpxCoordinates) {
    this.gpxCoordinates = gpxCoordinates;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setVideoUrl(String videoUrl) {
    this.videoUrl = videoUrl;
  }

  public void setAuthor(User user) {
    this.author = user;
  }
}
