package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.repository.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;

  @BeforeEach
  void setup() {
    UserRoleEntity userRole = new UserRoleEntity();
    userRole.setRole(UserRoleEnum.USER);
    roleRepository.save(userRole);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
  }

  @Test
  void getRegisterPage_returnsRegisterView() throws Exception {
    mockMvc.perform(get("/users/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"))
            .andExpect(model().attributeExists("userRegisterBindingModel"));
  }

  @Test
  void registerUser_withValidData_RedirectsToLogin() throws Exception {
    mockMvc.perform(post("/users/register")
            .param("username", "testuser")
            .param("firstName", "John")
            .param("lastName", "Doe")
            .param("gender", "MALE")
            .param("year", "1990")
            .param("month", "5")
            .param("day", "15")
            .param("email", "test@example.com")
            .param("password", "Password123!")
            .param("confirmPassword", "Password123!")
            .param("aboutMe", "I am a passionate coder.")
            .param("facebookAcc", "https://facebook.com/testuser")
            .param("instagramAcc", "https://instagram.com/testuser")
            .param("linkedIn", "https://linkedin.com/in/testuser")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("login"));

    Assertions.assertTrue(userRepository.findByEmail("test@example.com").isPresent());
  }

  @Test
  void registerUser_withInvalidData_redirectsBackWithErrors() throws Exception {
    mockMvc.perform(post("/users/register")
            .param("username", "")
            .param("email", "")
            .param("password", "123")
            .param("confirmPassword", "456") //
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("register"))
            .andExpect(flash().attributeExists("userRegisterBindingModel"))
            .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.userRegisterBindingModel"));
  }
}

