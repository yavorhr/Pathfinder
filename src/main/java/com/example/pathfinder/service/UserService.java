package com.example.pathfinder.service;

import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.model.view.UserNotificationViewModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

  void registerUser(UserRegisterServiceModel map);

  Optional<UserEntity> findById(Long id);

  boolean isUserNameAvailable(String username);

  boolean isEmailAvailable(String email);

  void deleteUser(String email);

  UserEntity findByEmail(String email);

  void updateUsersProfilePicture(String username, String url);

  UserUpdateStatusResponse changeAccess(String username);

  List<UserNotificationViewModel> findAllUsers();

  void updateUserRoles(String username, String[] roles);

  boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser);
}
