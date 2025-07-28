package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.model.view.UserNotificationViewModel;
import com.example.pathfinder.model.view.UserProfileViewModel;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final UserRolesService userRolesService;
  private final ApplicationEventPublisher eventPublisher;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRolesService userRolesService, ApplicationEventPublisher eventPublisher, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.userRolesService = userRolesService;
    this.eventPublisher = eventPublisher;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void registerUser(UserRegisterServiceModel serviceModel) {
    UserEntity user = this.modelMapper.map(serviceModel, UserEntity.class);

    user.setLevel(LevelEnum.BEGINNER);
    UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER);
    user.setRoles(Set.of(roleEntity));
    user.setPassword(this.passwordEncoder.encode(serviceModel.getPassword()));
    
    this.userRepository.save(user);

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
    UserEntity userEntity = this.userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ObjectNotFoundException("User with email: " + email + " does not exist!"));

    userEntity.getRoles().clear();
    this.userRepository.save(userEntity);

    this.userRepository.deleteByEmail(email);
  }

  @Override
  public UserEntity findByEmail(String email) {
    return this.userRepository
            .findByEmail(email)
            .orElseThrow(() -> new ObjectNotFoundException("User with email: " + email + " does not exist!"));
  }

  @Override
  public void updateUsersProfilePicture(String email, String url, String publicId) {
    UserEntity userEntity = this.userRepository.findByEmail(email)
            .orElseThrow(() -> new ObjectNotFoundException("User with the email " + email + " was not found!"));

    userEntity.setProfileImageUrl(url).setProfileImagePublicId(publicId);
    this.userRepository.save(userEntity);
  }

  @Override
  public UserUpdateStatusResponse changeAccess(String email) {
    UserEntity userEntity =
            this.userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the email " + email + " was not found!"));

    if (userEntity.isEnabled()) {
      userEntity.setEnabled(false);
      userEntity.setDisabledTime(LocalDateTime.now());
    } else {
      userEntity.setEnabled(true);
      userEntity.setDisabledTime(null);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public List<UserNotificationViewModel> findAllUsers() {

    List<UserNotificationViewModel> collect = this.userRepository
            .findAllUsersSortedByEnabledFalseAndUsernameAsc()
            .stream()
            .map(u -> {
              UserNotificationViewModel viewModel = this.modelMapper.map(u, UserNotificationViewModel.class);

              Set<UserRoleEnum> roleEnums = u.getRoles().stream()
                      .map(role -> UserRoleEnum.valueOf(role.getRole().name()))
                      .collect(Collectors.toSet());

              viewModel.setRoles(roleEnums);

              return viewModel;
            })
            .collect(Collectors.toList());

    return collect;

  }

  @Override
  public void updateUserRoles(String email, String[] roles) {
    UserEntity userEntity =
            this.userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the email " + email + " was not found!"));

    Set<UserRoleEntity> userRoles = new HashSet<>();

    Arrays.stream(roles).forEach(r -> {
      UserRoleEntity roleEntity = this.userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.valueOf(r));
      userRoles.add(roleEntity);
    });

    userEntity.setRoles(userRoles);
    this.userRepository.save(userEntity);
  }

  @Override
  public boolean isNotModifyingOwnProfile(String loggedInUser, String targetUser) {
    return !loggedInUser.equals(targetUser);

  }

  @Override
  public UserProfileViewModel updateUserData(UserProfileServiceModel serviceModel) {
    UserEntity userEntity = this.userRepository.findById(serviceModel.getId())
            .orElseThrow(() -> new ObjectNotFoundException("User with id: " + serviceModel.getId() + " does not exist!"));

    userEntity = updateUserEntity(serviceModel, userEntity);

    this.userRepository.save(userEntity);
    return this.modelMapper.map(userEntity, UserProfileViewModel.class);
  }

  @Override
  public boolean isUsernameAvailable(String username) {
    return this.userRepository.findByUsername(username).isEmpty();
  }

  @Override
  public void increaseUserFailedLoginAttempts(UserEntity user) {
    Integer failedAttempts = user.getFailedLoginAttempts();

    if (failedAttempts == null) {
      failedAttempts = 0;
    }

    user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

    if (user.getFailedLoginAttempts() == 5) {
      this.lockAccount(user);

      if (user.getTimesLocked() == 3) {
        this.disableUser(user);
      }

    } else {
      userRepository.save(user);
    }
  }

  private void disableUser(UserEntity user) {
    user.setEnabled(false);
    user.setDisabledTime(LocalDateTime.now());
    userRepository.save(user);
  }

  @Override
  public void lockAccount(UserEntity user) {
    user.setLockedAccountCounter(+1);
    user.setAccountLocked(true);
    user.setLockTime(LocalDateTime.now().plusMinutes(15));

    userRepository.save(user);
  }

  @Override
  public List<UserEntity> findLockedUsers() {
    return this.userRepository.findAllLockedUsers();
  }

  @Override
  public void updateUser(UserEntity user) {
    this.userRepository.save(user);
  }

  @Override
  public UserUpdateStatusResponse modifyLockStatus(String email) {
    UserEntity userEntity =
            this.userRepository
                    .findByEmail(email)
                    .orElseThrow(() -> new ObjectNotFoundException("User with the email " + email + " was not found!"));

    if (userEntity.isAccountLocked()) {
      userEntity.setAccountLocked(false);
    } else {
      userEntity.setAccountLocked(true);
    }

    this.userRepository.save(userEntity);

    return this.modelMapper.map(userEntity, UserUpdateStatusResponse.class);
  }

  @Override
  public void updateLastLoginTime(UserEntity user) {
    user.setLastLoginTime(LocalDateTime.now());
    userRepository.save(user);
  }

  @Override
  public List<UserEntity> findInactiveUsersSince(LocalDateTime oneYearAgo) {
    return this.userRepository.findInactiveUsersSinceOneYear(oneYearAgo);
  }

  @Override
  public void saveUpdatedUser(UserEntity user) {
    this.userRepository.save(user);
  }

  @Override
  public Page<UserNotificationViewModel> searchPaginatedUsersPerEmail(String email, Pageable pageable) {
    List<UserEntity> users = userRepository.findAllByEmailContainingIgnoreCase(email);

    int start = Math.toIntExact(pageable.getOffset());
    int end = Math.min(start + pageable.getPageSize(), users.size());

    List<UserEntity> pagedUsers = users.subList(start, end);
    List<UserNotificationViewModel> viewModels =
            pagedUsers.stream()
                    .map(u -> {
                      UserNotificationViewModel vm = modelMapper.map(u, UserNotificationViewModel.class);

                      Set<UserRoleEnum> roleEnums = u.getRoles().stream()
                              .map(UserRoleEntity::getRole)
                              .collect(Collectors.toSet());

                      vm.setRoles(roleEnums);
                      return vm;
                    })
                    .toList();

    return new PageImpl<>(viewModels, pageable, users.size());
  }

  @Override
  public void resetFailedAttempts(UserEntity user) {
    user.setFailedLoginAttempts(0);
    this.userRepository.save(user);
  }

  @Override
  public boolean isAccountLocked(UserEntity user) {

    if (user.isAccountLocked() && user.getLockTime().isAfter(LocalDateTime.now())) {
      return true;
    } else if (user.isAccountLocked() && user.getLockTime().isBefore(LocalDateTime.now())) {
      user.setAccountLocked(false);
      user.setFailedLoginAttempts(0);
      userRepository.save(user);
      return false;
    } else {
      return false;
    }
  }


  // Helpers
  private UserEntity updateUserEntity(UserProfileServiceModel serviceModel, UserEntity userEntity) {
    return userEntity
            .setFirstName(serviceModel.getFirstName())
            .setLastName(serviceModel.getLastName())
            .setAboutMe(serviceModel.getAboutMe())
            .setFacebookAcc(serviceModel.getFacebookAcc())
            .setInstagramAcc(serviceModel.getInstagramAcc())
            .setLinkedIn(serviceModel.getLinkedIn());
  }
}
