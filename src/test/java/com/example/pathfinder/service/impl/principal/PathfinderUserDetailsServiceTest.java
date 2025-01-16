package com.example.pathfinder.service.impl.principal;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PathfinderUserDetailsServiceTest {
  private UserEntity testUser;
  private UserRoleEntity adminRole, userRole;

  private PathfinderUserDetailsService serviceToTest;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void init() {
    //ARRANGE
    serviceToTest = new PathfinderUserDetailsService(mockUserRepository);

    adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    testUser = new UserEntity();
    testUser.setUsername("lucho");
    testUser.setEmail("lucho@lucho.com");
    testUser.setPassword("topsecret");
    testUser.setRoles(Set.of(adminRole, userRole));
  }

  @Test
  void testUserNotFound() {
    Assertions.assertThrows(
            UsernameNotFoundException.class,
            () -> serviceToTest.loadUserByUsername("invalid_user_email@not-exist.com")
    );
  }
}