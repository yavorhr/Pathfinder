package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.UserEntity;
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

  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRolesService userRolesService) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userRolesService = userRolesService;
  }

  @Override
  public void registerUser(UserRegisterServiceModel serviceModel) {
    UserEntity user = this.modelMapper.map(serviceModel, UserEntity.class);
    user.setLevel(LevelEnum.BEGINNER);
    UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER);
    user.setRoles(Set.of(roleEntity));
    //TODO: Admin will grant USER ROLE from the Admin panel
    this.userRepository.save(user);
  }

  @Override
  public void logout() {
//TODO
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
    return this.userRepository.findByUsername(username).isEmpty();
  }

  @Override
  public boolean isEmailAvailable(String email) {
    return this.userRepository.findByEmail(email).isEmpty();
  }

  @Override
  public boolean login(UserLoginServiceModel serviceModel) {
    Optional<UserEntity> loggedInUserOpt =
            this.userRepository.findByUsername(serviceModel.getUsername());

    if (loggedInUserOpt.isEmpty()) {
      logout();
      return false;
    } else {
      boolean success = serviceModel.getPassword().equals(loggedInUserOpt.get().getPassword());


      //TODO
/*      if (success) {
        UserEntity loggedInUserEntity = loggedInUserOpt.get();
        currentUser.saveUserToSession(loggedInUserEntity);
      }*/
      return success;
    }
  }

  @Override
  public Optional<UserEntity> findById(Long id) {
    return this.userRepository.findById(id);
  }
}
