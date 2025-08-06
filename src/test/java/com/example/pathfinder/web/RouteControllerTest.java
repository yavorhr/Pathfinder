package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.CategoryRepository;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class RouteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  private Route route1;
  private Route route2;

  private UserEntity author1;
  private UserEntity author2;

  private Category cat1;
  private Category cat2;

  private Comment comment1;
  private Comment comment2;

  @Autowired
  private RouteRepository routeRepository;
  @Autowired
  private CategoryRepository categoryRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CommentRepository commentRepository;

  @BeforeEach
  void setup() {

    comment1 = initComment("hey man, nice to meet you!", true, LocalDateTime.now());
    comment2 = initComment("what an amazing route!", true, LocalDateTime.now());

    cat1 = initCat(CategoryEnum.BICYCLE, "cat1_description");
    cat2 = initCat(CategoryEnum.CAR, "cat2_description");

    author1 = initAuthor("testUser1", "admin@abv.bg", "test1", "Ivan", "Ivanov", LocalDate.of(1989, 4, 4), GenderEnum.MALE, LocalDateTime.now(), true);
    author2 = initAuthor("testUser2", "user2@abv.bg", "test2", "Georgi", "Georgiev", LocalDate.of(1993, 9, 8), GenderEnum.MALE, LocalDateTime.now(), true);

    route1 = initRoute(LevelEnum.ADVANCED, 33, author1, Set.of(cat1), "route1", "very interesting route!", "testUrl1", Set.of(comment1));
    route2 = initRoute(LevelEnum.INTERMEDIATE, 55, author2, Set.of(cat2), "route2", "wowwwwwwwwwwwwwwwwwwwwwwwww!", "testUrl2", Set.of(comment1, comment2));
  }

  @AfterEach
  void tearDown() {
    routeRepository.deleteAll();
    categoryRepository.deleteAll();
    userRepository.deleteAll();
    commentRepository.deleteAll();
  }

  @Test
  void getRoutesPage_returnsRoutesViewWithRoutes() throws Exception {
    mockMvc.perform(get("/routes"))
            .andExpect(status().isOk())
            .andExpect(view().name("routes"))
            .andExpect(model().attributeExists("routes"));
  }

  @Test
  @WithMockUser
  void getRoutesPerCategory_returnsViewWithRoutes() throws Exception {
    String category = "car";

    MvcResult mvcResult = mockMvc.perform(get("/routes/" + category))
            .andExpect(status().isOk())
            .andExpect(view().name("route-category"))
            .andExpect(model().attributeExists("routes"))
            .andExpect(model().attribute("category", "car"))
            .andExpect(model().attributeExists("quote"))
            .andReturn();

    ModelAndView mav = mvcResult.getModelAndView();
    assert mav != null;
    List<RouteViewModel> routes = (List<RouteViewModel>) mav.getModel().get("routes");

    Assertions.assertNotNull(routes);
    Assertions.assertFalse(routes.isEmpty());

    routes.forEach(route -> {
      System.out.println(route.getName());
      Assertions.assertEquals("route2", route.getName());
      Assertions.assertEquals("wowwwwwwwwwwwwwwwwwwwwwwwww!", route.getDescription());
    });
  }

  @Test
  void getRouteDetailsPage_returnsViewAndRoute() throws Exception {

    MvcResult result = mockMvc.perform(get("/routes/details/" + route1.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("route-details"))
            .andExpect(model().attributeExists("routeDetails"))
            .andReturn();

    ModelAndView mav = result.getModelAndView();
    assert mav != null;

    RouteDetailsViewModel route = (RouteDetailsViewModel) mav.getModel().get("routeDetails");

    Assertions.assertNotNull(route);

    Assertions.assertEquals("route1", route.getName());
    Assertions.assertEquals("very interesting route!", route.getDescription());
  }

  @Test
  void getMostCommentedRoute_returnsViewAndRoute() throws Exception {
    MvcResult result = mockMvc.perform(get("/routes/most-commented"))
            .andExpect(status().isOk())
            .andExpect(view().name("route-details"))
            .andExpect(model().attributeExists("routeDetails")).andReturn();

    ModelAndView mav = result.getModelAndView();
    assert mav != null;

    RouteDetailsViewModel route = (RouteDetailsViewModel) mav.getModel().get("routeDetails");

    Assertions.assertNotNull(route);

    Assertions.assertEquals("route1", route.getName());
  }

  @Test
  void addRoute_returnsView() throws Exception {

 mockMvc.perform(get("/routes/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("add-route"));
  }

  @Test
  void addRoute_validationFails_redirectsBackToForm() throws Exception {
    MockMultipartFile gpxFile = new MockMultipartFile(
            "gpxCoordinates",
            "test.gpx",
            "application/octet-stream",
            "<gpx></gpx>".getBytes()
    );

    mockMvc.perform(multipart("/routes/add")
            .file(gpxFile)
            .param("name", "") // missing required name
            .param("description", "Too short")
            .param("level", "BEGINNER")
            .param("videoUrl", "https://example.com/video")
            .param("categories", "BICYCLE")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("add"))
            .andExpect(flash().attributeExists("routeAddBindingModel"))
            .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.routeAddBindingModel"));
  }

  @Test
  void addRoute_validationSuccessRedirectsToRouteDetailsId() throws Exception {
    MockMultipartFile gpxFile = new MockMultipartFile(
            "gpxCoordinates",
            "test.gpx",
            "application/octet-stream",
            "<gpx></gpx>".getBytes()
    );

    mockMvc.perform(multipart("/routes/add")
            .file(gpxFile)
            .param("name", "New Route")
            .param("description", "A very nice route")
            .param("level", "BEGINNER")
            .param("distance", "30")
            .param("videoUrl", "dQw4w9WgXcQ")
            .param("categories", "BICYCLE")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/routes/details/*"));
  }

  @Test
  void testDeleteRoute() throws Exception {

    mockMvc.perform(delete("/routes/delete")
            .with(csrf())
            .param("routeId", route1.getId().toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/routes"));
  }

  // Helpers
  private Comment initComment(String text, boolean approved, LocalDateTime created) {
    Comment comment = new Comment();
    comment.setTextContent(text);
    comment.setApproved(approved);
    comment.setCreated(created);

    commentRepository.save(comment);
    return comment;
  }

  private Route initRoute(LevelEnum advanced, int distance, UserEntity author1, Set<Category> categories, String name, String description, String videoUrl, Set<Comment> comments) {
    Route route = new Route();
    route.setLevel(advanced);
    route.setDistance(distance);
    route.setAuthor(author1);
    route.setCategories(categories);
    route.setName(name);
    route.setDescription(description);
    route.setVideoUrl(videoUrl);
    route.setComments(comments);

    routeRepository.save(route);
    return route;
  }

  private Category initCat(CategoryEnum catEnum, String description) {
    Category category = new Category();
    category.setName(catEnum);
    category.setDescription(description);

    categoryRepository.save(category);
    return category;
  }

  private UserEntity initAuthor(String username, String email, String password, String firstName, String lastName, LocalDate birthday, GenderEnum gender, LocalDateTime registrationDate, boolean isEnabled) {
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
            .setEnabled(isEnabled);

    userRepository.save(testUser);
    return testUser;
  }

}
