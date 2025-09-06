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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
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
  void registerUser_ShouldMapEncodeAssignRoleAndPublishEvent() {
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
  void updateUserLevelByNumberOfAddedRoutes_testBeginnerLevel() {
    testUser.setRoutes(List.of(new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.BEGINNER, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void updateUserLevelByNumberOfAddedRoutes_testIntermediateLevel() {
    testUser.setRoutes(List.of(
            new Route(), new Route(), new Route(),
            new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.INTERMEDIATE, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void updateUserLevelByNumberOfAddedRoutes_testAdvancedLevel() {
    testUser.setRoutes(List.of(
            new Route(), new Route(), new Route(), new Route()));

    serviceToTest.updateUserLevelByNumberOfAddedRoutes(new UpdateUserLevelEvent(testUser));

    Assertions.assertEquals(LevelEnum.ADVANCED, testUser.getLevel());
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void findById_returnsOptionalOfEmpty() {
    Mockito.when(mockedUserRepository.findById(2L))
            .thenReturn(Optional.empty());

    Optional<UserEntity> result = serviceToTest.findById(2L);

    Assertions.assertTrue(result.isEmpty());
  }

  @Test
  void findById_returnsOptionalOfUserEntity() {
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
  void findByUsername_returnsOptionalOfUser() {
    Mockito.when(mockedUserRepository.findByUsername("testUser"))
            .thenReturn(Optional.of(testUser));

    boolean result = serviceToTest.isUsernameAvailable("testUser");

    Assertions.assertFalse(result);
  }

  @Test
  void findByUsername_returnsOptionalEmpty() {
    Mockito.when(mockedUserRepository.findByUsername("available"))
            .thenReturn(Optional.empty());

    boolean result = serviceToTest.isUsernameAvailable("available");

    Assertions.assertTrue(result);
  }

  @Test
  void isEmailAvailable_returnsOptionalOfUser() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    //Act
    boolean result = serviceToTest.isEmailAvailable("test@abv.bg");

    Assertions.assertFalse(result);
  }

  @Test
  void isEmailAvailable_returnsOptionalEmpty() {
    Mockito.when(mockedUserRepository.findByEmail("available@abv.bg"))
            .thenReturn(Optional.empty());

    boolean result = serviceToTest.isEmailAvailable("available@abv.bg");

    Assertions.assertTrue(result);
  }

  @Test
  void findByEmail_returnsOptionalOfUserEntity() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    UserEntity userEntity = serviceToTest.findByEmail("test@abv.bg");
    Assertions.assertEquals(userEntity.getEmail(), "test@abv.bg");
  }

  @Test
  void findByEmail_throwObjectNotFoundException() {

    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.findByEmail("invalidEmail"));
  }

  @Test
  void deleteUser_deletesTheUserFromTheDB() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    serviceToTest.deleteUser("test@abv.bg");

    Assertions.assertTrue(testUser.getRoles().isEmpty());
    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(mockedUserRepository).deleteByEmail("test@abv.bg");
  }

  @Test
  void deleteUser_throwsObjNotFoundExc() {
    Mockito.when(mockedUserRepository.findByEmail("invalidEmail@abv.bg"))
            .thenReturn(Optional.empty());

    // Assert
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.deleteUser("invalidEmail@abv.bg"));

    Mockito.verify(mockedUserRepository, Mockito.never()).save(Mockito.any());
    Mockito.verify(mockedUserRepository, Mockito.never()).deleteByEmail(Mockito.any());
  }

  @Test
  void updateUserProfilePicture_updatesSuccessful() {
    Mockito.when(mockedUserRepository.findByEmail("test@abv.bg"))
            .thenReturn(Optional.of(testUser));

    this.serviceToTest.updateUsersProfilePicture("test@abv.bg", "testUrl", "testId");

    Mockito.verify(mockedUserRepository).save(testUser);

    Assertions.assertEquals("testUrl", testUser.getProfileImageUrl());
    Assertions.assertEquals("testId", testUser.getProfileImagePublicId());
  }

  @Test
  void userUpdateStatusResponse_throwsObjNotFoundExc() {
    Mockito.when(mockedUserRepository.findByEmail("invalidEmail@abv.bg"))
            .thenReturn(Optional.empty());

    // Assert
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.changeAccess("invalidEmail@abv.bg"));
  }

  @Test
  void changeAccess_whenUserIsEnabled() {
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
  void changeAccess_whenUserIsDisabled() {
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
  void findAllUsers_mappedToUserNotificationViewModels() {
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
  void updateUserRoles_throwsObjNotFoundExc() {
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.updateUserRoles("invalidEmail@abv.bg", new String[3]));
  }

  @Test
  void updateUserRoles_successfullyUpdatesRoles() {
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
  void isNotModifyingOwnProfile_returnsTrue() {
    Assertions.assertTrue(serviceToTest.isNotModifyingOwnProfile("loggedInUser", "invalidUser"));
  }

  @Test
  void updateUserData_throwsObjNotFoundExc() {
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

  @Test
  void increaseUserFailedLoginAttempts_whenFailedAttemptsIsNull() {
    Assertions.assertEquals(0, testUser.getFailedLoginAttempts());
  }

  @Test
  void increaseFailedLoginAttempts_LessThan5() {
    UserEntity user = new UserEntity();
    user.setFailedLoginAttempts(2);

    serviceToTest.increaseUserFailedLoginAttempts(user);

    Assertions.assertEquals(3, user.getFailedLoginAttempts());
    Mockito.verify(mockedUserRepository).save(user);
  }

  @Test
  void increaseFailedLoginAttempts_Exactly5FailedAttempts_UserLocked() {
    UserEntity user = new UserEntity();
    user.setFailedLoginAttempts(4);

    serviceToTest.increaseUserFailedLoginAttempts(user);

    Assertions.assertEquals(0, user.getFailedLoginAttempts());
    Assertions.assertTrue(user.isAccountLocked());
    Assertions.assertEquals(1, user.getTimesLocked());
  }

  @Test
  void increaseFailedLoginAttempts_Exactly3LockingTimes_UserDisabled() {
    UserEntity user = new UserEntity();
    user.setLockedAccountCounter(2);
    user.setFailedLoginAttempts(4);

    serviceToTest.increaseUserFailedLoginAttempts(user);

    Assertions.assertEquals(5, user.getFailedLoginAttempts());
    Assertions.assertTrue(user.isAccountLocked());
    Assertions.assertFalse(user.isEnabled());
    Assertions.assertEquals(1, user.getTimesLocked());
  }

  @Test
  void testDisableUser_directCall() {
    UserEntity user = new UserEntity();
    user.setEnabled(true);

    serviceToTest.disableUser(user);

    Assertions.assertFalse(user.isEnabled());
    Assertions.assertNotNull(user.getDisabledTime());
    Mockito.verify(mockedUserRepository).save(user);
  }

  @Test
  void testFindLockedUsers_ReturnsLockedUsers() {
    // Arrange
    UserEntity user1 = new UserEntity();
    user1.setUsername("locked1");
    user1.setAccountLocked(true);

    UserEntity user2 = new UserEntity();
    user2.setUsername("locked2");
    user2.setAccountLocked(true);

    List<UserEntity> lockedUsers = List.of(user1, user2);

    // Mock repository call
    Mockito.when(mockedUserRepository
            .findAllLockedUsers())
            .thenReturn(lockedUsers);

    // Act
    List<UserEntity> result = serviceToTest.findLockedUsers();

    // Assert
    Assertions.assertEquals(2, result.size());
    Assertions.assertTrue(result.contains(user1));
    Assertions.assertTrue(result.contains(user2));

    // Verify repository delegation
    Mockito.verify(mockedUserRepository).findAllLockedUsers();
  }

  @Test
  void modifyLockStatus_throwsObjNotFoundExc() {
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> serviceToTest.modifyLockStatus("invalidEmail"));
  }

  @Test
  void modifyLockStatus_setAccountToLocked() {
    //Arrange
    testUser.setAccountLocked(false);

    UserUpdateStatusResponse response = new UserUpdateStatusResponse();
    response.setAccountLocked(true);
    response.setEmail(testUser.getEmail());

    //Mock
    Mockito.when(modelMapper.map(testUser, UserUpdateStatusResponse.class))
            .thenReturn(response);

    Mockito.when(mockedUserRepository
            .findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    //Act
    UserUpdateStatusResponse responseResult =
            this.serviceToTest.modifyLockStatus(testUser.getEmail());

    //Assert
    Assertions.assertEquals(responseResult.getEmail(), testUser.getEmail());
    Assertions.assertTrue(responseResult.isAccountLocked());
    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(mockedUserRepository).findByEmail(testUser.getEmail());
  }

  @Test
  void modifyLockStatus_setAccountToUnlocked() {
    //Arrange
    testUser.setAccountLocked(true);

    UserUpdateStatusResponse response = new UserUpdateStatusResponse();
    response.setAccountLocked(false);
    response.setEmail(testUser.getEmail());

    //Mock
    Mockito.when(modelMapper.map(testUser, UserUpdateStatusResponse.class))
            .thenReturn(response);

    Mockito.when(mockedUserRepository
            .findByEmail(testUser.getEmail()))
            .thenReturn(Optional.of(testUser));

    //Act
    UserUpdateStatusResponse responseResult =
            this.serviceToTest.modifyLockStatus(testUser.getEmail());

    //Assert
    Assertions.assertEquals(responseResult.getEmail(), testUser.getEmail());
    Assertions.assertFalse(responseResult.isAccountLocked());
    Mockito.verify(mockedUserRepository).save(testUser);
    Mockito.verify(mockedUserRepository).findByEmail(testUser.getEmail());
  }

  @Test
  void updateUser_persistUserToDB() {
    serviceToTest.updateUser(testUser);
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void updateLastLoginTime() {
    this.serviceToTest.updateLastLoginTime(testUser);
    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void findInactiveUsersSince_returnsListOfUsers() {
    //Arrange
    testUser.setLastLoginTime(LocalDateTime.of(2023, 3, 3, 3, 21));

    UserEntity user2 = new UserEntity();
    user2.setUsername("locked1");
    user2.setLastLoginTime(LocalDateTime.of(2022, 3, 3, 3, 21));

    Mockito.when(mockedUserRepository.
            findInactiveUsersSinceOneYear(LocalDateTime.of(2024, 1, 1, 00, 01)))
            .thenReturn(List.of(testUser, user2));

    //Act
    List<UserEntity> users =
            this.serviceToTest.findInactiveUsersSince(LocalDateTime.of(2024, 1, 1, 00, 01));

    Assertions.assertEquals(2, users.size());
    Assertions.assertTrue(users.contains(testUser));
    Assertions.assertTrue(users.contains(user2));
  }

  @Test
  void searchPaginatedUsersPerEmail_ReturnsCorrectPage() {
    // Arrange
    String emailSearch = "user";
    Pageable pageable = PageRequest.of(0, 2);

    UserRoleEntity roleAdmin = new UserRoleEntity();
    roleAdmin.setRole(UserRoleEnum.ADMIN);

    UserRoleEntity roleUser = new UserRoleEntity();
    roleUser.setRole(UserRoleEnum.USER);

    testUser.setRoles(Set.of(roleAdmin));

    UserEntity user2 = new UserEntity();
    user2.setEmail("user2@abv.bg");
    user2.setRoles(Set.of(roleUser));

    UserEntity user3 = new UserEntity();
    user3.setEmail("user3@abv.bg");
    user3.setRoles(Set.of(roleAdmin, roleUser));

    List<UserEntity> allMatchingUsers = List.of(testUser, user2, user3);

    Mockito.when(mockedUserRepository.findAllByEmailContainingIgnoreCase(emailSearch))
            .thenReturn(allMatchingUsers);

    Mockito.when(modelMapper.map(Mockito.any(UserEntity.class), Mockito.eq(UserNotificationViewModel.class)))
            .thenAnswer(invocation -> new UserNotificationViewModel());

    // Act
    Page<UserNotificationViewModel> result = serviceToTest.searchPaginatedUsersPerEmail(emailSearch, pageable);

    // Assert
    Assertions.assertEquals(2, result.getContent().size());
    Assertions.assertEquals(3, result.getTotalElements());

    // Verify repository call
    Mockito.verify(mockedUserRepository).findAllByEmailContainingIgnoreCase(emailSearch);

    Mockito.verify(modelMapper, Mockito.times(2)).map(Mockito.any(UserEntity.class), Mockito.eq(UserNotificationViewModel.class));
  }

  @Test
  void resetFailedAttempts() {
    testUser.setFailedLoginAttempts(3);

    this.serviceToTest.resetFailedAttempts(testUser);

    Assertions.assertEquals(0, testUser.getFailedLoginAttempts());


    Mockito.verify(mockedUserRepository).save(testUser);
  }

  @Test
  void testIsAccountLocked_lockTimeInFuture_returnsTrue() {
    UserEntity user = new UserEntity();
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().plusMinutes(10));

    boolean result = serviceToTest.isAccountLocked(user);

    Assertions.assertTrue(result);
    Mockito.verify(mockedUserRepository, Mockito.never()).save(Mockito.any());
  }

  @Test
  void testIsAccountLocked_LockTimeExpired_UnlocksAndReturnsFalse() {
    UserEntity user = new UserEntity();
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().minusMinutes(10));
    user.setFailedLoginAttempts(3);

    boolean result = serviceToTest.isAccountLocked(user);

    Assertions.assertFalse(result);
    Assertions.assertFalse(user.isAccountLocked());
    Assertions.assertEquals(0, user.getFailedLoginAttempts());
    Mockito.verify(mockedUserRepository).save(user);
  }

  @Test
  void testIsAccountLocked_NotLocked_ReturnsFalse() {
    UserEntity user = new UserEntity();
    user.setAccountLocked(false); // or default false
    user.setLockTime(null); // if allowed

    boolean result = serviceToTest.isAccountLocked(user);

    Assertions.assertFalse(result);
    Mockito.verify(mockedUserRepository, Mockito.never()).save(Mockito.any());
  }
}