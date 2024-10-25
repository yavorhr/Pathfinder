package com.example.pathfinder.service;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.enums.CategoryEnum;

import java.util.Optional;

public interface CategoryService {

  Optional<Category> findByName(CategoryEnum categoryEnum);

}
