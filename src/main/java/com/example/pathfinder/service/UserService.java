package com.example.pathfinder.service;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;

import java.util.Optional;

public interface UserService {

  void registerUser(UserRegisterServiceModel map);

  void logout();

  UserProfileServiceModel findUserServiceById(Long id);

  boolean isUserNameAvailable(String username);

  boolean isEmailAvailable(String email);

  boolean login(UserLoginServiceModel serviceModel);

  Optional<User> findById(Long id);
}
