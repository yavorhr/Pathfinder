package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.CategoryEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
  private String description;
  private CategoryEnum name;

  public Category() {
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
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
}
