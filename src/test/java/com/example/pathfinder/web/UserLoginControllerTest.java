package com.example.pathfinder.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class UserLoginControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @ParameterizedTest
  @CsvSource({
          "ACCOUNT_LOCKED, Your account is locked. Try again in 15 minutes.",
          "ACCOUNT_DISABLED, Your account is disabled. Admin will contact you.",
          "INVALID_CREDENTIALS, Invalid credentials. Please try again.",
          "ACCOUNT_EXPIRED, Your account has expired.",
          "USER_NOT_FOUND, User with this email does not exist.",
          "UNKNOWN_ERROR, An unknown error occurred."  // tests the catch block
  })
   public void login_testDifferentErrorMessages(String errorType, String expectedMessage) throws Exception {
    mockMvc.perform(get("/users/login")
            .param("errorType", errorType))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attribute("login_error_message", expectedMessage));
  }

  @Test
  void login_returnsLoginViewWithoutError() throws Exception {
    mockMvc.perform(get("/users/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeDoesNotExist("login_error_message"));
  }
}
