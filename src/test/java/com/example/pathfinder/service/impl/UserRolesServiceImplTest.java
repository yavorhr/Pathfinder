package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.repository.UserRoleRepository;
import com.example.pathfinder.service.UserRolesService;
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
public class UserRolesServiceImplTest {

  private UserRolesService serviceToTest;
  private UserRoleEntity testRole;

  @Mock
  private UserRoleRepository mockRepository;

  @BeforeEach
  void init() {
    this.serviceToTest = new UserRolesServiceImpl(mockRepository);

    this.testRole = new UserRoleEntity();
    testRole.setRole(UserRoleEnum.ADMIN);
    testRole.setId(1L);
  }

  @Test
  void userRoleEntityNotExist() {
    Mockito.when(mockRepository.findByRole(UserRoleEnum.MODERATOR))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.findRoleEntityByRoleEnum(UserRoleEnum.MODERATOR));
  }

  @Test
  void findCategoryByName() {

    Mockito.when(mockRepository
            .findByRole(UserRoleEnum.ADMIN))
            .thenReturn(Optional.of(testRole));

    UserRoleEntity roleEntityByRoleEnum = this.serviceToTest.findRoleEntityByRoleEnum(UserRoleEnum.ADMIN);

    Assertions.assertEquals(roleEntityByRoleEnum.getRole(), testRole.getRole());
    Assertions.assertEquals(roleEntityByRoleEnum.getId(), testRole.getId());
  }
}
