package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.LevelEnum;
import com.example.pathfinder.repository.CategoryRepository;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import jakarta.transaction.Transactional;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

  @Autowired
  private RouteRepository routeRepository;
  @Autowired
  private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
      cat1 = initCat(CategoryEnum.BICYCLE, "cat1_description");
      cat2 = initCat(CategoryEnum.CAR, "cat2_description");

      author1 = initAuthor("testUser1", "user1@abv.bg", "test1", "Ivan", "Ivanov", LocalDate.of(1989, 4, 4), GenderEnum.MALE, LocalDateTime.now(), true, 1L);
      author2 = initAuthor("testUser2", "user2@abv.bg", "test2", "Georgi", "Georgiev", LocalDate.of(1993, 9, 8), GenderEnum.MALE, LocalDateTime.now(), true, 2L);

      route1 = initRoute(LevelEnum.ADVANCED, 33, author1, Set.of(cat1), 1L, "route1");
      route2 = initRoute(LevelEnum.INTERMEDIATE, 55, author2, Set.of(cat2), 2L, "route2");
  }

  @AfterEach
  void tearDown() {
    routeRepository.deleteAll();
    categoryRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Test
  void getRoutesPage_returnsRoutesViewWithRoutes() throws Exception {
    mockMvc.perform(get("/routes"))
            .andExpect(status().isOk())
            .andExpect(view().name("routes"))
            .andExpect(model().attributeExists("routes"));
  }

  // Helpers
  private Route initRoute(LevelEnum advanced, int distance, UserEntity author1, Set<Category> categories, long id, String name) {
    Route route = new Route();
    route.setLevel(advanced);
    route.setDistance(distance);
    route.setAuthor(author1);
    route.setCategories(categories);
    route.setId(id);
    route.setName(name);

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

  private UserEntity initAuthor(String username, String email, String password, String firstName, String lastName, LocalDate birthday, GenderEnum gender, LocalDateTime registrationDate, boolean isEnabled, long id) {
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
            .setId(1L);

    userRepository.save(testUser);

    return testUser;
  }

}
