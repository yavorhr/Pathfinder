package com.example.pathfinder.service;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.enums.CategoryEnum;

public interface CategoryService {

  Category findByName(CategoryEnum categoryEnum);

}
