package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Picture;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.service.PictureAddServiceModel;
import com.example.pathfinder.repository.PictureRepository;
import com.example.pathfinder.service.PictureService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {

  private PictureService serviceToTest;

  @Mock
  private RouteService mockedRouteService;

  @Mock
  private UserService mockedUserService;

  @Mock
  private PictureRepository mockedPictureRepository;

  private Picture picture;
  private UserEntity author;
  private Route route;

  @BeforeEach
  void setup() {
    this.serviceToTest = new PictureServiceImpl(mockedUserService,
            mockedRouteService,
            mockedPictureRepository);

    //Mock author
    author = new UserEntity();
    author.setFirstName("John");
    author.setLastName("Doe");
    author.setEmail("john.doe@abv.bg")
            .setId(33L);

    //Mock route
    route = new Route();
    route.setAuthor(author);
    route.setId(22L);

    //Mock picture
    picture = new Picture();
    picture
            .setAuthor(author)
            .setRoute(route)
            .setTitle("title")
            .setPublicId("testId")
            .setUrl("testUrl")
            .setId(1L);
  }

  @Test
  void findPictureUrls_returnsListOfStrings() {
    List<String> urls = List.of("str1", "str2");

    Mockito.when(mockedPictureRepository.getAllPictureUrls()).thenReturn(urls);

    List<String> result = this.serviceToTest.findPictureUrls();

    Assertions.assertEquals(urls.size(), result.size());
    Assertions.assertTrue(result.contains("str1"));
    Assertions.assertTrue(result.contains("str2"));

    Mockito.verify(mockedPictureRepository).getAllPictureUrls();
  }

  @Test
  void addPicture() {
    PictureAddServiceModel serviceModel = new PictureAddServiceModel();
    // 1.Arrange
    serviceModel
            .setUrl(picture.getUrl())
            .setPublicId(picture.getPublicId())
            .setTitle(picture.getTitle())
            .setAuthorId(author.getId())
            .setRouteId(route.getId());

    // 1.1 Mock dependencies
    Mockito.when(mockedRouteService.findRouteById(22L)).thenReturn(Optional.of(route));
    Mockito.when(mockedUserService.findById(33L)).thenReturn(Optional.of(author));

    // Act
    serviceToTest.addPicture(serviceModel, author.getId());

    // Assert
    ArgumentCaptor<Picture> pictureCaptor = ArgumentCaptor.forClass(Picture.class);
    Mockito.verify(mockedPictureRepository).save(pictureCaptor.capture());

    Picture saved = pictureCaptor.getValue();

    Assertions.assertEquals("title", saved.getTitle());
    Assertions.assertEquals("testUrl", saved.getUrl());
    Assertions.assertEquals("testId", saved.getPublicId());
    Assertions.assertEquals(route, saved.getRoute());
    Assertions.assertEquals(author, saved.getAuthor());
  }

  @Test
  void deletePicture(){
    this.serviceToTest.deletePicture(picture.getPublicId());
    Mockito.verify(mockedPictureRepository).deleteByPublicId(picture.getPublicId());
  }
}
