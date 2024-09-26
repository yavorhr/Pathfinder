package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean doesUsernameAlreadyExist(String username) {
    return this.userRepository.findUserByUsername(username).isPresent();
  }

  @Override
  public void registerUser(UserRegisterServiceModel serviceModel) {
    User user = this.modelMapper.map(serviceModel, User.class);
    user.setLevel(LevelEnum.BEGINNER);
    //TODO: Admin will grant USER ROLE from the Admin panel
    this.userRepository.save(user);
  }
}
