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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class PathfinderUserDetailsServiceTest {
  private PathfinderUserDetailsService serviceToTest;
  private UserRoleEntity adminRole, userRole;
  private UserEntity testUser;

  @Mock
  private UserRepository mockUserRepository;

  @BeforeEach
  void init() {
    //Arrange before each test
    serviceToTest = new PathfinderUserDetailsService(mockUserRepository);

    adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    testUser = new UserEntity();
    testUser.setId(1L);
    testUser.setEnabled(true);
    testUser.setUsername("lucho");
    testUser.setEmail("lucho@lucho.com");
    testUser.setPassword("topsecret");
    testUser.setRoles(Set.of(adminRole, userRole));
    testUser.setRegistrationDate(LocalDateTime.now());
  }

  @Test
  void testUserNotFound() {
    Assertions.assertThrows(
            UsernameNotFoundException.class,
            () -> serviceToTest.loadUserByUsername("invalid_user_email@not-exist.com")
    );
  }

  @Test
  void testUserFound() {
    // Arrange

    Mockito.when(mockUserRepository
            .findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    // Act
    var actual = serviceToTest.loadUserByUsername(testUser.getEmail());

    // Assert
    String expectedRoles = "ROLE_ADMIN, ROLE_USER";
    String actualRoles = actual.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).collect(
                    Collectors.joining(", "));

    Assertions.assertEquals(actual.getUsername(), testUser.getEmail());
    Assertions.assertEquals(expectedRoles, actualRoles);
  }

  @Test
  void testMapToUserDetails() {
    // Arrange

    Mockito.when(mockUserRepository
            .findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    // Act
    UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());

    // Assert
    Assertions.assertNotNull(userDetails);
    Assertions.assertEquals(1L, ((PathfinderUser) userDetails).getId());
    Assertions.assertEquals(testUser.getEmail(), userDetails.getUsername());
    Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
    Assertions.assertTrue(userDetails.isEnabled());
    Assertions.assertTrue(userDetails.isAccountNonExpired());
    Assertions.assertTrue(userDetails.isCredentialsNonExpired());
    Assertions.assertTrue(userDetails.isAccountNonLocked());
    Assertions.assertEquals(2, userDetails.getAuthorities().size());
  }

  @Test
  void testGetUserEntity() {
    Mockito.when(mockUserRepository
            .findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());
    Assertions.assertEquals(testUser, ((PathfinderUser) userDetails).getUserEntity());
  }

  @Test
  void testAccountNonExpired_WhenLastLoginLessThanOneYearAgo() {
    testUser.setLastLoginTime(LocalDateTime.now().minusMonths(6));
    Mockito.when(mockUserRepository.findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());

    Assertions.assertTrue(userDetails.isAccountNonExpired());
  }

  @Test
  void testAccountExpired_WhenLastLoginMoreThanOneYearAgo() {
    testUser.setLastLoginTime(LocalDateTime.now().minusYears(2));
    Mockito.when(mockUserRepository.findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getEmail());

    Assertions.assertFalse(userDetails.isAccountNonExpired());
  }

  // test on first constructor of PathfinderUser
  @Test
  void testPathfinderUserFistConstructor() {
    // Arrange
    Collection<SimpleGrantedAuthority> authorities = Set.of(
            new SimpleGrantedAuthority("ROLE_USER"),
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    // Act
//    PathfinderUser pathfinderUser = new PathfinderUser(
//            testUser.getId(),
//            testUser.getUsername(),
//            testUser.getPassword(),
//            authorities);

    // Assert
//    Assertions. assertEquals(testUser.getId(), pathfinderUser.getId());
//    Assertions.assertEquals(testUser.getUsername(), pathfinderUser.getUsername());
//    Assertions.assertEquals(testUser.getPassword(), pathfinderUser.getPassword());
//    Assertions.assertTrue(pathfinderUser.isEnabled());
//    Assertions.assertTrue(pathfinderUser.isAccountNonExpired());
//    Assertions.assertTrue(pathfinderUser.isCredentialsNonExpired());
//    Assertions.assertTrue(pathfinderUser.isAccountNonLocked());
//    Assertions.assertEquals(2, pathfinderUser.getAuthorities().size());
  }

}