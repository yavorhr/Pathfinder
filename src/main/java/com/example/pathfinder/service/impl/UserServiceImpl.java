package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.ProfilePicture;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.ProfilePictureRepository;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.service.events.UpdateUserLevelEvent;
import com.example.pathfinder.service.events.UserRegisteredEvent;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final UserRolesService userRolesService;
  private final ApplicationEventPublisher eventPublisher;
  private final ProfilePictureRepository profilePictureRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRolesService userRolesService, ApplicationEventPublisher eventPublisher, ProfilePictureRepository profilePictureRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userRolesService = userRolesService;
    this.eventPublisher = eventPublisher;
    this.profilePictureRepository = profilePictureRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void registerUser(UserRegisterServiceModel serviceModel) {
    UserEntity user = this.modelMapper.map(serviceModel, UserEntity.class);

    user.setLevel(LevelEnum.BEGINNER);
    UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER);
    user.setRoles(Set.of(roleEntity));
    user.setPassword(this.passwordEncoder.encode(serviceModel.getPassword()));

    user = this.userRepository.save(user);

    ProfilePicture profilePicture = new ProfilePicture();
    profilePicture.setUser(user);
    this.profilePictureRepository.save(profilePicture);

    eventPublisher.publishEvent(new UserRegisteredEvent(
            this,
            LocalDateTime.now(),
            serviceModel.getUsername(),
            serviceModel.getEmail()));
  }

  @EventListener
  public void updateUserLevelByNumberOfAddedRoutes(UpdateUserLevelEvent event) {
    UserEntity user = event.getUser();

    if (user.getRoutes().size() <= 3) {
      user.setLevel(LevelEnum.BEGINNER);
    } else if (user.getRoutes().size() >= 6) {
      user.setLevel(LevelEnum.INTERMEDIATE);
    } else {
      user.setLevel(LevelEnum.ADVANCED);
    }

    this.userRepository.save(user);
  }

  @Override
  public Optional<UserEntity> findById(Long id) {
    return this.userRepository.findById(id);
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
  @Transactional
  public void deleteUser(String email) {
    this.userRepository.deleteByEmail(email);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return this.userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ObjectNotFoundException("User with email: " + email + " does not exist!"));
  }

  @Override
  public void updateUsersProfilePicture(String email, String url) {
    UserEntity userEntity = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new ObjectNotFoundException("User with the email " + email + " was not found!"));
//    userEntity.setProfileImage(url);

    this.userRepository.save(userEntity);
  }
}
