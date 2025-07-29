package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.events.UpdateUserLevelEvent;
import com.example.pathfinder.service.events.UserRegisteredEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  private UserServiceImpl serviceToTest;
  @Mock
  private UserRepository mockedUserRepository;
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private UserRolesService userRolesService;
  @Mock
  private ApplicationEventPublisher eventPublisher;
  @Mock
  private PasswordEncoder passwordEncoder;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    serviceToTest = new UserServiceImpl(
            mockedUserRepository,
            modelMapper,
            userRolesService,
            eventPublisher,
            passwordEncoder
    );

    testUser = new UserEntity();
    testUser.setUsername("testUser")
            .setEmail("test@abv.bg")
            .setRoutes(new ArrayList<>())
            .setId(1L);
  }

  @Test
  void testRegisterUser_ShouldMapEncodeAssignRoleAndPublishEvent() {
    //Arrange
    UserRegisterServiceModel serviceModel = new UserRegisterServiceModel();

    serviceModel
            .setEmail("test@abv.bg")
            .setUsername("test")
            .setPassword("plainPassword");

    UserEntity mappedUser = new UserEntity();

    UserRoleEntity roleEntity = new UserRoleEntity();
    roleEntity.setRole(UserRoleEnum.USER);

    Mockito.when(modelMapper.map(serviceModel, UserEntity.class)).thenReturn(mappedUser);
    Mockito.when(userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER)).thenReturn(roleEntity);
    Mockito.when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

    // Act
    serviceToTest.registerUser(serviceModel);

    // Assert
    Assertions.assertEquals(LevelEnum.BEGINNER, mappedUser.getLevel());
    Assertions.assertEquals(Set.of(roleEntity), mappedUser.getRoles());
    Assertions.assertEquals("encodedPassword", mappedUser.getPassword());

    Mockito.verify(mockedUserRepository).save(mappedUser);

    ArgumentCaptor<UserRegisteredEvent> eventCaptor = ArgumentCaptor.forClass(UserRegisteredEvent.class);
    Mockito.verify(eventPublisher).publishEvent(eventCaptor.capture());

    UserRegisteredEvent event = eventCaptor.getValue();

    Assertions.assertEquals(serviceModel.getUsername(), event.getUsername());
    Assertions.assertEquals(serviceModel.getEmail(), event.getEmail());
    Assertions.assertNotNull(event.getTimestamp());
  }

  @Test
  void testBeginnerLevelWhenRoutesAreThreeOrLess() {
    testUser.setRoutes(List.of(new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.BEGINNER, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void testIntermediateLevel_whenUserHas6OrMoreRoutes() {
    testUser.setRoutes(List.of(
            new Route(), new Route(), new Route(),
            new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.INTERMEDIATE, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void testAdvancedLevel_whenUserHas4Or5Routes() {
    testUser.setRoutes(List.of(
            new Route(), new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.ADVANCED, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void findByIdShouldThrowObjectNotFoundException() {
    Mockito.when(mockedUserRepository.findById(2L))
            .thenReturn(Optional.empty());

    Optional<UserEntity> result = serviceToTest.findById(2L);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  void findByIdReturnsOptionalOfUserEntity() {
    //Arrange
    Mockito.when(mockedUserRepository.findById(1L))
            .thenReturn(Optional.of(testUser));

    //Act
    Optional<UserEntity> result = serviceToTest.findById(1L);

    //Assert
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals(testUser, result.get());
  }

  @Test
  void testIfUsernameIsOccupied() {
    Mockito.when(mockedUserRepository.findByUsername("testUser"))
            .thenReturn(Optional.of(testUser));

    boolean result = serviceToTest.isUserNameAvailable("testUser");

    Assertions.assertFalse(result);
  }

  @Test
  void testIfUsernameIsAvailable() {
    Mockito.when(mockedUserRepository.findByUsername("available"))
            .thenReturn(Optional.empty());

    boolean result = serviceToTest.isUserNameAvailable("available");

    Assertions.assertTrue(result);
  }

  @Test
  void testIfEmailIsOccupied() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    //Act
    boolean result = serviceToTest.isEmailAvailable("test@abv.bg");

    Assertions.assertFalse(result);
  }

  @Test
  void testIfEmailIsAvailable() {
    Mockito.when(mockedUserRepository.findByEmail("available@abv.bg"))
            .thenReturn(Optional.empty());

    boolean result = serviceToTest.isEmailAvailable("available@abv.bg");

    Assertions.assertTrue(result);
  }


}
