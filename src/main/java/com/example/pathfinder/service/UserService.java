package com.example.pathfinder.service;

import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;

public interface UserService {
  boolean doesUsernameAlreadyExist(String username);

  void registerUser(UserRegisterServiceModel map);

  boolean doesEmailExist(String email);

  UserLoginServiceModel findUserByUserNameAndPassword(String username, String password);

  void logout();

  UserProfileServiceModel findById(Long id);

  boolean isUserNameAvailable(String username);

  boolean isEmailAvailable(String email);

  boolean login(UserLoginServiceModel serviceModel);
}
