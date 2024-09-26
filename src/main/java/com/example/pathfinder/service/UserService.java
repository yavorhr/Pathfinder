package com.example.pathfinder.service;

import com.example.pathfinder.model.service.UserRegisterServiceModel;

public interface UserService {
  boolean doesUsernameAlreadyExist(String username);

  void registerUser(UserRegisterServiceModel map);
}
