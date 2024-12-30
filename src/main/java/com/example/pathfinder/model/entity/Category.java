package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.CategoryEnum;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
  private String description;
  private CategoryEnum name;
  private Set<Route> routes;

  public Category() {
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  @ManyToMany(mappedBy = "categories")
  public Set<Route> getRoutes() {
    return routes;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  public CategoryEnum getName() {
    return name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setName(CategoryEnum name) {
    this.name = name;
  }

  public void setRoutes(Set<Route> routes) {
    this.routes = routes;
  }
}
