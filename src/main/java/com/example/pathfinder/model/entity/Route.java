package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "routes")
public class Route extends BaseEntity {
  private String description;
  private String gpxCoordinates;
  private LevelEnum level;
  private String name;
  private String videoUrl;
  private Integer distance;
  private UserEntity author;
  private Set<Category> categories;
  private Set<Picture> pictures;
  private Set<Comment> comments;

  public Route() {
    this.pictures = new HashSet<>();
  }

  @Column(nullable = false)
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
          name = "routes_categories",
          joinColumns = @JoinColumn(name = "route_id"),
          inverseJoinColumns = @JoinColumn(name = "category_id"))
  public Set<Category> getCategories() {
    return categories;
  }

  @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  public Set<Comment> getComments() {
    return comments;
  }

  @OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  public Set<Picture> getPictures() {
    return pictures;
  }

  @ManyToOne
  public UserEntity getAuthor() {
    return author;
  }

  @Column(nullable = false)
  public Integer getDistance() {
    return distance;
  }

  @Lob
  @Column(name = "description", length = 10485760)
  public String getDescription() {
    return description;
  }

  @Lob
  @Column(name = "gpx_coordinates", length = 10485760)
  public String getGpxCoordinates() {
    return gpxCoordinates;
  }

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  public LevelEnum getLevel() {
    return level;
  }

  @Column(nullable = false, unique = true)
  public String getName() {
    return name;
  }

  @Column(name = "video_url")
  public String getVideoUrl() {
    return videoUrl;
  }

  public Route setPictures(Set<Picture> pictures) {
    this.pictures = pictures;
    return this;
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

  public void setAuthor(UserEntity user) {
    this.author = user;
  }

  public Route setDistance(Integer distance) {
    this.distance = distance;
    return this;
  }

  public Route setComments(Set<Comment> comments) {
    this.comments = comments;
    return this;
  }
}
