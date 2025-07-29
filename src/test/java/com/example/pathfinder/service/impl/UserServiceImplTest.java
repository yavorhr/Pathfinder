package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.events.UserRegisteredEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
  private UserServiceImpl serviceToTest;
  @Mock
  private UserRepository userRepository;
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private UserRolesService userRolesService;
  @Mock
  private ApplicationEventPublisher eventPublisher;
  @Mock
  private PasswordEncoder passwordEncoder;


  @BeforeEach
  void setUp() {
    serviceToTest = new UserServiceImpl(
            userRepository,
            modelMapper,
            userRolesService,
            eventPublisher,
            passwordEncoder
    );
  }

  @Test
  void testRegisterUser_ShouldMapEncodeAssignRoleAndPublishEvent() {
    //Arrange
    UserRegisterServiceModel serviceModel = new UserRegisterServiceModel();

    serviceModel
            .setEmail("test@abv.bg")
            .setUsername("test")
            .setPassword("plainPassword")
            .setBirthday(LocalDate.now())
            .setGender(GenderEnum.MALE)
            .setFirstName("Test").setLastName("Testov");

    UserEntity mappedUser = new UserEntity();

    UserRoleEntity roleEntity = new UserRoleEntity();
    roleEntity.setRole(UserRoleEnum.USER);

    Mockito.when(modelMapper.map(serviceModel, UserEntity.class)).thenReturn(mappedUser);
    Mockito.when(userRolesService.findRoleEntityByRoleEnum(UserRoleEnum.USER)).thenReturn(roleEntity);
    Mockito.when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

    // Act
    serviceToTest.registerUser(serviceModel);

    // Assert
    Assertions.assertEquals(LevelEnum.BEGINNER, mappedUser.getLevel());
    Assertions.assertEquals(Set.of(roleEntity), mappedUser.getRoles());
    Assertions.assertEquals("encodedPassword", mappedUser.getPassword());

    Mockito.verify(userRepository).save(mappedUser);

    ArgumentCaptor<UserRegisteredEvent> eventCaptor = ArgumentCaptor.forClass(UserRegisteredEvent.class);
    Mockito.verify(eventPublisher).publishEvent(eventCaptor.capture());

    UserRegisteredEvent event = eventCaptor.getValue();

    Assertions.assertEquals(serviceModel.getUsername(), event.getUsername());
    Assertions.assertEquals(serviceModel.getEmail(), event.getEmail());
    Assertions.assertNotNull(event.getTimestamp());
  }
}
