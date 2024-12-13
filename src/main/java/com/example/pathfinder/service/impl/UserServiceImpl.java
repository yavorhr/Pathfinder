package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
  public void registerUser(UserRegisterServiceModel serviceModel) {
    User user = this.modelMapper.map(serviceModel, User.class);
    user.setLevel(LevelEnum.BEGINNER);
    UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER);
    user.setRoles(Set.of(roleEntity));
    //TODO: Admin will grant USER ROLE from the Admin panel
    this.userRepository.save(user);
  }

  @Override
  public void logout() {
    this.currentUser.clear();
  }

  @Override
  public UserProfileServiceModel findUserServiceById(Long id) {
    return
            this.userRepository
                    .findById(id)
                    .map(user -> this.modelMapper.map(user, UserProfileServiceModel.class))
                    .orElse(null);
  }

  @Override
  public boolean isUserNameAvailable(String username) {
    return this.userRepository.findUserByUsername(username).isEmpty();
  }

  @Override
  public boolean isEmailAvailable(String email) {
    return this.userRepository.findByEmail(email).isEmpty();
  }

  @Override
  public boolean login(UserLoginServiceModel serviceModel) {
    Optional<User> loggedInUserOpt =
            this.userRepository.findUserByUsername(serviceModel.getUsername());

    if (loggedInUserOpt.isEmpty()) {
      logout();
      return false;
    } else {
      boolean success = serviceModel.getPassword().equals(loggedInUserOpt.get().getPassword());

      if (success) {
        User loggedInUserEntity = loggedInUserOpt.get();
        currentUser.saveUserToSession(loggedInUserEntity);
      }
      return success;
    }
  }

  @Override
  public Optional<User> findById(Long id) {
    return this.userRepository.findById(id);
  }
}
