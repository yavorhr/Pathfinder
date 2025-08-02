package com.example.pathfinder.web;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN, USER"})
class CommentRestController {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void contextLoadsAndMockMvcWorks() throws Exception {
    mockMvc.perform(get("/api/10/comments"))
            .andExpect(status().isOk());
  }
}