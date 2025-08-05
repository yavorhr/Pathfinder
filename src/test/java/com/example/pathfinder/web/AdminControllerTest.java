package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.RoleUpdateRequest;
import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.view.UserNotificationViewModel;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.repository.UserRoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class AdminControllerTest {

  private UserEntity testUser;
  private UserRoleEntity adminRole, moderatorRole, userRole;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    adminRole = new UserRoleEntity(UserRoleEnum.ADMIN);
    moderatorRole = new UserRoleEntity(UserRoleEnum.MODERATOR);
    userRole = new UserRoleEntity(UserRoleEnum.USER);
    roleRepository.saveAll(List.of(adminRole, moderatorRole, userRole));

    testUser = new UserEntity();
    testUser.setPassword("password");
    testUser.setEnabled(true);
    testUser.setUsername("admin");
    testUser.setEmail("admin@abv.bg");
    testUser.setFirstName("admin");
    testUser.setLastName("adminov").setBirthday(LocalDate.now()).setGender(GenderEnum.MALE);
    testUser.setRoles(Set.of(adminRole));
    testUser.setAccountLocked(false);

    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    roleRepository.deleteAll();
  }

  @Test
  void viewNotifications_shouldReturnNotificationsPage() throws Exception {
    UserNotificationViewModel viewModel = new UserNotificationViewModel();
    viewModel.setEmail("admin@abv.bg").setRoles(testUser.getRoles().stream().map(UserRoleEntity::getRole).collect(Collectors.toSet()));

    mockMvc.perform(get("/admin/notifications")
            .param("query", "")
            .param("page", "0")
            .param("size", "5"))
            .andExpect(status().isOk())
            .andExpect(view().name("notifications"))
            .andExpect(model().attributeExists("usersPage", "users", "loggedInUserEmail", "query", "currentPage", "totalPages"));
  }

  @Test
  void deleteUserByEmail_shouldReturnOkResponse() throws Exception {
    mockMvc.perform(delete("/admin/api/remove-user/admin@abv.bg")
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string("User deleted successfully!"));
  }

  @Test
  void changeUserAccess_shouldReturnUpdatedStatus() throws Exception {

    mockMvc.perform(put("/admin/change-user-access/admin@abv.bg")
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("admin@abv.bg"))
            .andExpect(jsonPath("$.enabled").value(false))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
  }

  @Test
  void updateRoles_shouldUpdateAndReturnSuccessMessage() throws Exception {
    RoleUpdateRequest request = new RoleUpdateRequest();
    request.setEmail(testUser.getEmail());
    request.setRoles(new String[]{"USER", "MODERATOR"});

    String json = new ObjectMapper().writeValueAsString(request);

    mockMvc.perform(patch("/admin/api/update-roles")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(content().string("Roles updated successfully"))
            .andDo(print());
  }

  @Test
  void changeUserLockStatus_shouldReturnStatusResponse() throws Exception {
    UserUpdateStatusResponse response = new UserUpdateStatusResponse();
    response.setEmail(testUser.getEmail());
    response.setAccountLocked(false);
    response.setEnabled(testUser.isEnabled());
    response.setRoles(testUser.getRoles().stream().map(UserRoleEntity::getRole).collect(Collectors.toSet()));

    String json = new ObjectMapper().writeValueAsString(response);

    mockMvc.perform(put("/admin/change-user-lock-status/admin@abv.bg")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("admin@abv.bg"))
            .andExpect(jsonPath("$.accountLocked").value(true))
            .andDo(print());
  }
}

