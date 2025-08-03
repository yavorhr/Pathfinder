package com.example.pathfinder.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@WithMockUser(username = "yavor@abv.bg", roles = {"ADMIN, USER"})
@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {
  private static final String COMMENT_1 = "Well... it is a bit trick sometimes... :(";
  private static final String COMMENT_2 = "hey Spring is cool!";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RouteRepository routeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private UserEntity testUser;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setPassword("password");
    testUser.setUsername("yavor");
    testUser.setEmail("yavor@abv.bg");
    testUser.setFirstName("admin");
    testUser.setLastName("adminov").setBirthday(LocalDate.now()).setGender(GenderEnum.MALE);

    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    routeRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void testGetComments() throws Exception {
    var route = initComments(initRoute());

    mockMvc.perform(get("/api/" + route.getId() + "/comments")).
            andExpect(status().isOk()).
            andExpect(jsonPath("$", hasSize(2))).
            andExpect(jsonPath("$.[0].textContent", is(COMMENT_1))).
            andExpect(jsonPath("$.[1].textContent", is(COMMENT_2)));
  }

  private Route initRoute() {
    Route testRoute = new Route();
    testRoute.setName("Testing route");
    testRoute.setDistance(30);
    testRoute.setLevel(LevelEnum.ADVANCED);

    return routeRepository.save(testRoute);
  }

  private Route initComments(Route route) {

    Comment comment1 = new Comment();

    comment1.setCreated(LocalDateTime.now());
    comment1.setAuthor(testUser);
    comment1.setTextContent(COMMENT_1);
    comment1.setApproved(true);
    comment1.setRoute(route);

    Comment comment2 = new Comment();
    comment2.setCreated(LocalDateTime.now());
    comment2.setAuthor(testUser);
    comment2.setTextContent(COMMENT_2);
    comment2.setApproved(true);
    comment2.setRoute(route);

    route.setComments(Set.of(comment1, comment2));

    return routeRepository.save(route);
  }

}