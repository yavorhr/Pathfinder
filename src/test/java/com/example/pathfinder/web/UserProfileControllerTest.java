package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class UserProfileControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;

  private UserEntity user;


  @BeforeEach
  void setup() {
    user = initUser("admin", "admin@abv.bg", "test1", "Ivan", "Ivanov", LocalDate.of(1989, 4, 4), GenderEnum.MALE, LocalDateTime.now(), true);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  private UserEntity initUser(String username, String email, String password, String firstName, String lastName, LocalDate birthday, GenderEnum gender, LocalDateTime registrationDate, boolean isEnabled) {
    UserEntity testUser = new UserEntity();

    testUser
            .setUsername(username)
            .setEmail(email)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setBirthday(birthday)
            .setGender(gender)
            .setRegistrationDate(registrationDate)
            .setPassword(password)
            .setEnabled(isEnabled)
            .setLevel(LevelEnum.ADVANCED);

    userRepository.save(testUser);
    return testUser;
  }
}
