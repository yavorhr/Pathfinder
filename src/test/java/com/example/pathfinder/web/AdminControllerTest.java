package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.view.UserNotificationViewModel;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.repository.UserRoleRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.service.UserService;
import org.hamcrest.text.MatchesPattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"})
public class AdminControllerTest {

  private UserEntity testUser;
  private UserRoleEntity role;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;


  @BeforeEach
  void setUp() {
    role = new UserRoleEntity(UserRoleEnum.ADMIN);
    roleRepository.save(role);

    testUser = new UserEntity();
    testUser.setPassword("password");
    testUser.setUsername("admin");
    testUser.setEmail("admin@abv.bg");
    testUser.setFirstName("admin");
    testUser.setLastName("adminov").setBirthday(LocalDate.now()).setGender(GenderEnum.MALE);
    testUser.setRoles(Set.of(role));

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
  void deleteUserById_shouldReturnOkResponse() throws Exception {
    mockMvc.perform(post("/admin/remove-user/admin@abv.bg")
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("admin@abv.bg"))).andDo(print());
  }


}

