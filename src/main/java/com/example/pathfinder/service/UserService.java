package com.example.pathfinder.service;

import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.model.view.UserNotificationViewModel;
import com.example.pathfinder.model.view.UserProfileViewModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

  void registerUser(UserRegisterServiceModel map);

  Optional<UserEntity> findById(Long id);

  boolean isUserNameAvailable(String username);

  boolean isEmailAvailable(String email);

  void deleteUser(String email);

  UserEntity findByEmail(String email);

  void updateUsersProfilePicture(String email, String url, String publicId);

  UserUpdateStatusResponse changeAccess(String username);

  List<UserNotificationViewModel> findAllUsers();

  void updateUserRoles(String username, String[] roles);

  boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser);

  UserProfileViewModel updateUserData(UserProfileServiceModel map);

  boolean isUsernameAvailable(String username);

  void increaseUserFailedLoginAttempts(UserEntity user);

  void resetFailedAttempts(UserEntity user);

  boolean isAccountLocked(UserEntity user);

  void lockAccount(UserEntity user);

  List<UserEntity> findLockedUsers();

  void updateUser(UserEntity user);

  UserUpdateStatusResponse modifyLockStatus(String email);
}
