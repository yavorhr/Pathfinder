package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.GenderEnum;
import com.example.pathfinder.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@abv.bg", roles = {"ADMIN, USER"})
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
  void tearDown(){
    userRepository.deleteAll();
  }

  @Test
  void uploadProfilePicture_shouldReturnUrlAndPublicId() throws Exception {
    MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test content".getBytes());

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
    MockMultipartFile file = new MockMultipartFile("file", "bad.jpg", MediaType.IMAGE_JPEG_VALUE, "bad content".getBytes());

    Mockito.when(cloudinaryService.upload(any(), eq("users-pictures")))
            .thenThrow(new IOException("Upload failed"));

    mockMvc.perform(multipart("/api/profile/image-upload")
            .file(file)
            .with(csrf()))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.error").value("Upload failed"));
  }

}
