package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.repository.UserRoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class PageViewsControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private StringRedisTemplate redis;
  private ZSetOperations<String, String> zset;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;

  @BeforeAll
  void setUp() {
    zset = redis.opsForZSet();
  }

  @BeforeEach
  void initRedisData() {
    // Clear Redis keys
    redis.delete("views:sorted");

    zset.add("views:sorted", "/home", 100);
    zset.add("views:sorted", "/about", 50);
    zset.add("views:sorted", "/contact", 25);
  }

  @AfterEach
  void tearDown() {
    redis.delete("views:sorted");
  }

  @Test
  void mostViewed_shouldReturnTopPagesAndModelAttributes() throws Exception {
    mockMvc.perform(get("/admin/statistics"))
            .andExpect(status().isOk())
            .andExpect(view().name("most-viewed"))
            .andExpect(model().attributeExists("topPages", "chartLabels", "chartData"))
            .andExpect(content().string(containsString("/home")))
            .andExpect(content().string(containsString("/about")))
            .andExpect(content().string(containsString("/contact")));
  }

  @Test
  void resetStats_shouldClearAllViewKeysAndRedirect() throws Exception {

    mockMvc.perform(post("/admin/statistics/reset")
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/admin/statistics"));

  }
}
