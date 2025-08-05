package com.example.pathfinder.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void testGetHomePage_returnsHomePage() throws Exception {

    mockMvc.perform(get("/")).
            andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("pictures"));
  }

  @Test
  void testGetHomePage_returnsAboutPage() throws Exception {

    mockMvc.perform(get("/about")).
            andExpect(status().isOk())
            .andExpect(view().name("about"));
  }
}
