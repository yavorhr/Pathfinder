package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.repository.CategoryRepository;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
  @Mock
  private CategoryRepository mockRepository;
  private Category testCategory;
  private CategoryServiceImpl serviceToTest;

  @BeforeEach
  void init() {
    this.serviceToTest = new CategoryServiceImpl(mockRepository);

    testCategory = new Category();
    testCategory.setName(CategoryEnum.CAR);
    testCategory.setDescription("test_description");
    testCategory.setId(1L);
  }

  @Test
  void categoryNotExist() {
    Mockito.when(mockRepository.findByName(CategoryEnum.BICYCLE))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(ObjectNotFoundException.class, () -> serviceToTest.findByName(CategoryEnum.BICYCLE));
  }

  @Test
  void findCategoryByName() {

    Mockito.when(mockRepository
            .findByName(testCategory.getName()))
            .thenReturn(Optional.of(testCategory));

    Category actualCategory = this.serviceToTest.findByName(CategoryEnum.CAR);

    Assertions.assertEquals(actualCategory.getName(), testCategory.getName());
    Assertions.assertEquals(actualCategory.getDescription(), testCategory.getDescription());
    Assertions.assertEquals(actualCategory.getId(), testCategory.getId());
  }
}
