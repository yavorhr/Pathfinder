package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Comment;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.view.CommentViewModel;
import com.example.pathfinder.repository.CommentRepository;
import com.example.pathfinder.service.CommentService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

  @Mock
  private CommentRepository mockedCommentRepository;

  private CommentService serviceToTest;

  @Mock
  private RouteService mockedRouteService;

  @Mock
  private UserService mockedUserService;


  @BeforeEach
  void setUp() {
    serviceToTest = new CommentServiceImpl(mockedCommentRepository,
            mockedRouteService,
            mockedUserService);
  }

  @Test
  void testGetAllComments_RouteNotFound_ThrowsException() {
    Long routeId = 2L;
    Mockito.when(mockedRouteService.findRouteById(routeId))
            .thenReturn(Optional.empty());

    Assertions.assertThrows(ObjectNotFoundException.class, () -> {
      serviceToTest.getAllComments(routeId);
    });
  }

  @Test
  void testGetAllComments_RouteExists_MapsAndReturnsComments() {
    // 1. Arrange
    Long routeId = 1L;

    // -- Mock Author
    UserEntity author = new UserEntity();
    author.setFirstName("John");
    author.setLastName("Doe");

    // -- Mock CommentEntity
    Comment comment = new Comment();
    comment.setId(123L);
    comment.setApproved(true);
    comment.setCreated(LocalDateTime.of(2023, 7, 30, 14, 15));
    comment.setTextContent("Nice route!");
    comment.setAuthor(author);

    // -- Mock RouteEntity with one comment
    Route mockRoute = new Route();
    mockRoute.setComments(Set.of(comment));

    // -- Mock routeService
    Mockito.when(mockedRouteService.findRouteById(routeId)).thenReturn(Optional.of(mockRoute));

    // 2. Act
    List<CommentViewModel> result = serviceToTest.getAllComments(routeId);

    // 3. Assert
    Assertions.assertEquals(1, result.size());
    CommentViewModel viewModel = result.get(0);
    Assertions.assertEquals(123L, viewModel.getCommentId());
    Assertions.assertTrue(viewModel.isApproved());
    Assertions.assertEquals("2023-07-30 14:15:00", viewModel.getCreated());
    Assertions.assertEquals("Nice route!", viewModel.getTextContent());
    Assertions.assertEquals("John Doe", viewModel.getAuthor());
  }
}
