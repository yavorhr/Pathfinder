package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.ProfileUpdateBindingModel;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class UserProfileControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  ObjectMapper objectMapper;
  private UserEntity user;

  @BeforeEach
  void setup() {
    user = initUser("admin", "admin@abv.bg", "test1", "Ivan", "Ivanov", LocalDate.of(1989, 4, 4), GenderEnum.MALE, LocalDateTime.now(), true);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  void profilePage_returnsCorrectModelAndView() throws Exception {
    String testEmail = user.getEmail();

    mockMvc.perform(get("/users/profile"))
            .andExpect(status().isOk())
            .andExpect(view().name("profile"))
            .andExpect(model().attributeExists("userViewModel"))
            .andExpect(model().attribute("userViewModel",
                    Matchers.hasProperty("email", Matchers.equalTo(testEmail))))
            .andExpect(model().attribute("userViewModel",
                    Matchers.hasProperty("firstName", Matchers.equalTo(user.getFirstName()))));
  }

  @Test
  void updateProfile_withValidParamsReturnsOkAndUpdatedViewModel() throws Exception {
    ProfileUpdateBindingModel bindingModel = new ProfileUpdateBindingModel();
    bindingModel.setFirstName("update_first_name");
    bindingModel.setLastName("updated_last_name");
    bindingModel.setId(user.getId());

    String json = objectMapper.writeValueAsString(bindingModel);

    mockMvc.perform(patch("/users/profile/edit")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("update_first_name"))
            .andExpect(jsonPath("$.lastName").value("updated_last_name"));
  }

  @Test
  void updateProfile_withInvalidParamsReturnsBadRequest() throws Exception {
    ProfileUpdateBindingModel bindingModel = new ProfileUpdateBindingModel();
    bindingModel.setLastName("");
    String json = objectMapper.writeValueAsString(bindingModel);

    mockMvc.perform(patch("/users/profile/edit")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.lastName").exists());
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

    userRepository.saveAndFlush(testUser);
    return testUser;
  }
}
