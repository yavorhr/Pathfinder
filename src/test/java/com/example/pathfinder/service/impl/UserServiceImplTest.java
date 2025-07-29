package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.model.view.UserNotificationViewModel;
import com.example.pathfinder.model.view.UserProfileViewModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.events.UpdateUserLevelEvent;
import com.example.pathfinder.service.events.UserRegisteredEvent;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
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

import java.util.*;

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
            .setProfileImageUrl("")
            .setEnabled(true)
            .setProfileImagePublicId("")
            .setEmail("test@abv.bg")
            .setRoutes(new ArrayList<>())
            .setId(1L);

    testUser.setRoles(new HashSet<>());
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
  void findByIdShouldReturnOptionalOfEmptyResult() {
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

    boolean result = serviceToTest.isUsernameAvailable("testUser");

    Assertions.assertFalse(result);
  }

  @Test
  void testIfUsernameIsAvailable() {
    Mockito.when(mockedUserRepository.findByUsername("available"))
            .thenReturn(Optional.empty());

    boolean result = serviceToTest.isUsernameAvailable("available");

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

  @Test
  void findUserByEmailShouldReturnOptionalOfUserEntity() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    UserEntity userEntity = serviceToTest.findByEmail("test@abv.bg");
    Assertions.assertEquals(userEntity.getEmail(), "test@abv.bg");
  }

  @Test
  void findUserByEmailShouldThrowObjectNotFoundException() {

    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.findByEmail("invalidEmail"));
  }

  @Test
  void deleteUserByEmailShouldDeleteTheUserFromTheDB() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    serviceToTest.deleteUser("test@abv.bg");

    Assertions.assertTrue(testUser.getRoles().isEmpty());
    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(mockedUserRepository).deleteByEmail("test@abv.bg");
  }

  @Test
  void deleteUserByEmailShouldThrowObjNotFoundExc() {
    Mockito.when(mockedUserRepository.findByEmail("invalidEmail@abv.bg"))
            .thenReturn(Optional.empty());

    // Assert
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.deleteUser("invalidEmail@abv.bg"));

    Mockito.verify(mockedUserRepository, Mockito.never()).save(Mockito.any());
    Mockito.verify(mockedUserRepository, Mockito.never()).deleteByEmail(Mockito.any());
  }

  @Test
  void updateUserProfilePictureSuccessfully() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    this.serviceToTest.updateUsersProfilePicture("test@abv.bg", "testUrl", "testId");

    Mockito.verify(mockedUserRepository).save(testUser);

    Assertions.assertEquals("testUrl", testUser.getProfileImageUrl());
    Assertions.assertEquals("testId", testUser.getProfileImagePublicId());
  }

  @Test
  void userUpdateStatusResponseThrowsObjNotFoundExc() {
    Mockito.when(mockedUserRepository.findByEmail("invalidEmail@abv.bg"))
            .thenReturn(Optional.empty());

    // Assert
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.changeAccess("invalidEmail@abv.bg"));
  }

  @Test
  void changeAccessWhenUserIsEnabled() {
    // Arrange
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    UserUpdateStatusResponse mockResponse = new UserUpdateStatusResponse();

    Mockito.when(modelMapper.map(testUser, UserUpdateStatusResponse.class))
            .thenReturn(mockResponse);

    // Act
    UserUpdateStatusResponse result = serviceToTest.changeAccess("test@abv.bg");

    // Assert
    Assertions.assertFalse(testUser.isEnabled());
    Assertions.assertNotNull(testUser.getDisabledTime());

    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(modelMapper).map(testUser, UserUpdateStatusResponse.class);
    Assertions.assertEquals(mockResponse, result);
  }

  @Test
  void changeAccessWhenUserIsDisabled() {
    // Arrange
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    testUser.setEnabled(false);

    UserUpdateStatusResponse mockResponse = new UserUpdateStatusResponse();

    Mockito.when(modelMapper.map(testUser, UserUpdateStatusResponse.class))
            .thenReturn(mockResponse);

    // Act
    UserUpdateStatusResponse result = serviceToTest.changeAccess("test@abv.bg");

    // Assert
    Assertions.assertTrue(testUser.isEnabled());
    Assertions.assertNull(testUser.getDisabledTime());

    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(modelMapper).map(testUser, UserUpdateStatusResponse.class);
    Assertions.assertEquals(mockResponse, result);
  }

  @Test
  void findAllUsersMappedToUserNotificationViewModels() {
    // Arrange

    // testUser
    UserRoleEntity role2 = new UserRoleEntity();
    role2.setRole(UserRoleEnum.USER);
    testUser.setRoles(Set.of(role2));

    UserNotificationViewModel viewModel2 = new UserNotificationViewModel();
    viewModel2.setUsername("testUser");
    viewModel2.setRoles(Set.of(UserRoleEnum.USER));

    // User 2
    UserEntity testUser2 = new UserEntity();
    testUser2.setUsername("alice");
    testUser2.setEnabled(false);
    UserRoleEntity role1 = new UserRoleEntity();
    role1.setRole(UserRoleEnum.ADMIN);
    testUser2.setRoles(Set.of(role1));

    UserNotificationViewModel viewModel1 = new UserNotificationViewModel();
    viewModel1.setUsername("alice");
    viewModel1.setRoles(Set.of(UserRoleEnum.ADMIN));

    // Mock repository and mapper
    Mockito.when(mockedUserRepository.findAllUsersSortedByEnabledFalseAndUsernameAsc())
            .thenReturn(List.of(testUser2, testUser));

    Mockito.when(modelMapper.map(testUser2, UserNotificationViewModel.class)).thenReturn(viewModel1);
    Mockito.when(modelMapper.map(testUser, UserNotificationViewModel.class)).thenReturn(viewModel2);

    // Act
    List<UserNotificationViewModel> result = serviceToTest.findAllUsers();

    // Assert
    Assertions.assertEquals(2, result.size());
    Assertions.assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("alice")));
    Assertions.assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("testUser")));
  }

  @Test
  void updateUserRolesShouldThrowsObjNotFoundExc() {
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.updateUserRoles("invalidEmail@abv.bg", new String[3]));
  }

  @Test
  void updateUserRolesSuccessfully() {
    //Arrange
    UserRoleEntity adminRole = new UserRoleEntity();
    adminRole.setRole(UserRoleEnum.ADMIN);

    UserRoleEntity userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);

    String[] roles = {"ADMIN", "USER"};

    Mockito.when(mockedUserRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
    Mockito.when(userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER)).thenReturn(userRole);
    Mockito.when(userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.ADMIN)).thenReturn(adminRole);

    //Act
    serviceToTest.updateUserRoles(testUser.getEmail(), roles);

    //Assert
    Mockito.verify(mockedUserRepository).save(testUser);
    Assertions.assertEquals(2, testUser.getRoles().size());
    Assertions.assertTrue(testUser.getRoles().contains(adminRole));
    Assertions.assertTrue(testUser.getRoles().contains(userRole));
  }

  @Test
  void userIsNotAbleToModifyOwnProfile() {
    Assertions.assertTrue(serviceToTest.isNotModifyingOwnProfile("loggedInUser", "invalidUser"));
  }

  @Test
  void updateUserDataShouldThrowObjNotFoundExc() {
    Long userId = 99L;

    UserProfileServiceModel serviceModel = new UserProfileServiceModel();
    serviceModel.setId(userId);

    Mockito.when(mockedUserRepository.findById(userId)).thenReturn(Optional.empty());

    Assertions.assertThrows(ObjectNotFoundException.class, () -> {
      serviceToTest.updateUserData(serviceModel);
    });

    Mockito.verify(mockedUserRepository).findById(userId);
    Mockito.verifyNoMoreInteractions(mockedUserRepository);
  }

  @Test
  void testUpdateUserData_successfullyUpdatesUser() {
    // Arrange
    Long userId = 1L;

    UserProfileServiceModel serviceModel = new UserProfileServiceModel();
    serviceModel.setId(userId);
    serviceModel.setFirstName("UpdatedName");

    testUser.setFirstName("OldName");

    UserEntity updatedUser = new UserEntity();
    updatedUser.setId(userId);
    updatedUser.setFirstName("UpdatedName");

    UserProfileViewModel expectedViewModel = new UserProfileViewModel();
    expectedViewModel.setFirstName("UpdatedName");

    Mockito.when(mockedUserRepository.findById(userId)).thenReturn(Optional.of(testUser));
    Mockito.when(mockedUserRepository.save(Mockito.any(UserEntity.class))).thenReturn(updatedUser);
    Mockito.when(modelMapper.map(Mockito.any(UserEntity.class), Mockito.eq(UserProfileViewModel.class)))
            .thenReturn(expectedViewModel);

    // Act
    UserProfileViewModel result = serviceToTest.updateUserData(serviceModel);

    // Assert
    Assertions.assertNotNull(result);
    Assertions.assertEquals("UpdatedName", result.getFirstName());

    Mockito.verify(mockedUserRepository).findById(userId);
    Mockito.verify(mockedUserRepository).save(Mockito.any(UserEntity.class));
    Mockito.verify(modelMapper).map(Mockito.any(UserEntity.class), Mockito.eq(UserProfileViewModel.class));
  }
}

