package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.util.cloudinary.CloudinaryImage;
import com.example.pathfinder.util.cloudinary.CloudinaryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN", "USER"})
public class PicturesControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private CloudinaryService cloudinaryService;
  private UserEntity testUser;
  @Autowired
  private UserRepository userRepository;
  @MockBean
  private RouteService routeService;

  @BeforeEach
  void setUp() {
    testUser = new UserEntity();
    testUser.setPassword("password");
    testUser.setUsername("admin");
    testUser.setEmail("admin@abv.bg");
    testUser.setFirstName("admin");
    testUser.setLastName("adminov").setBirthday(LocalDate.now()).setGender(GenderEnum.MALE);
    testUser = userRepository.save(testUser);
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  void uploadProfilePicture_shouldReturnUrlAndPublicId() throws Exception {
    MockMultipartFile file =
            new MockMultipartFile("file", "bad.jpg",
                    MediaType.IMAGE_JPEG_VALUE, "bad content".getBytes());

    CloudinaryImage uploaded = new CloudinaryImage();
    uploaded.setPublicId("test_public_id").setUrl("https://cloudinary.com/test.jpg");

    Mockito.when(cloudinaryService.upload(any(), eq("users-pictures")))
            .thenReturn(uploaded);

    mockMvc.perform(multipart("/api/profile/image-upload")
            .file(file)
            .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.url").value("https://cloudinary.com/test.jpg"))
            .andExpect(jsonPath("$.publicId").value("test_public_id"));
  }

  @Test
  void uploadProfilePicture_shouldHandleIOException() throws Exception {
    MockMultipartFile file =
            new MockMultipartFile("file", "bad.jpg",
                    MediaType.IMAGE_JPEG_VALUE, "bad content".getBytes());

    Mockito.when(cloudinaryService.upload(any(), eq("users-pictures")))
            .thenThrow(new IOException("Upload failed"));

    mockMvc.perform(multipart("/api/profile/image-upload")
            .file(file)
            .with(csrf()))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("Upload failed"));
  }

  @Test
  void deleteProfileImage_shouldDeleteSuccessfully() throws Exception {
    Mockito.when(cloudinaryService.delete("some-public-id")).thenReturn(true);

    mockMvc.perform(delete("/api/profile/image-delete")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(Map.of("publicId", "some-public-id"))))
            .andExpect(status().isOk());
  }

  @Test
  void deleteProfileImage_shouldHandleException() throws Exception {
    Mockito.doThrow(new RuntimeException("Failed to delete")).when(cloudinaryService).delete("fail-id");

    mockMvc.perform(delete("/api/profile/image-delete")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(Map.of("publicId", "fail-id"))))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("Failed to delete image"));
  }

  @Test
  void deletePicture_shouldDeleteAndRedirect() throws Exception {
    String publicId = "test_public_id";
    Long routeId = 123L;

    Mockito.when(routeService.isOwnerOrIsAdmin(eq("admin@abv.bg"), eq(routeId))).thenReturn(true);
    Mockito.when(cloudinaryService.delete(publicId)).thenReturn(true);

    mockMvc.perform(delete("/pictures/delete")
            .param("public_id", publicId)
            .param("routeId", String.valueOf(routeId))
            .with(csrf()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/routes/details/" + routeId));
  }
}
