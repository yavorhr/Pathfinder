package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final UserRolesService userRolesService;
  private final CurrentUser currentUser;

  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRolesService userRolesService, CurrentUser currentUser) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userRolesService = userRolesService;
    this.currentUser = currentUser;
  }

  @Override
  public boolean doesUsernameAlreadyExist(String username) {
    return this.userRepository.findUserByUsername(username).isPresent();
  }

  @Override
  public void registerUser(UserRegisterServiceModel serviceModel) {
    User user = this.modelMapper.map(serviceModel, User.class);
    user.setLevel(LevelEnum.BEGINNER);
    UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER);
    user.setRoles(Set.of(roleEntity));
    //TODO: Admin will grant USER ROLE from the Admin panel
    this.userRepository.save(user);
  }

  @Override
  public boolean doesEmailExist(String email) {
    return this.userRepository.findByEmail(email).isPresent();

  }

  @Override
  public UserLoginServiceModel findUserByUserNameAndPassword(String username, String password) {
    User userEntity = this.userRepository.findByUsernameAndPassword(username, password);

    return this.modelMapper.map(userEntity, UserLoginServiceModel.class);

  }

  @Override
  public void loginUser(Long id, String username) {
    currentUser.setId(id).setUsername(username);
  }
}
