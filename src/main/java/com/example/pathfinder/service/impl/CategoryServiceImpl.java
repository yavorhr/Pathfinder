package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.repository.CategoryRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public Category findByName(CategoryEnum categoryEnum) {
    return this.categoryRepository.findByName(categoryEnum)
            .orElseThrow(() -> new ObjectNotFoundException("Category with name " + categoryEnum + " was not found!"));
  }
}
