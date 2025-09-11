package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.*;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.service.RouteDetailsServiceModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.service.events.UpdateUserLevelEvent;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class RouteServiceImplTest {
  private Route route1, route2;
  private RouteViewModel vm1, vm2;
  private UserEntity admin, user;
  private Category cat1, cat2;

  @Mock
  private RouteRepository routeRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private UserService userService;

  @Mock
  private CategoryService categoryService;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @InjectMocks
  private RouteServiceImpl serviceToTest;

  @BeforeEach
  void setUp() {
    //Mock users
    admin = new UserEntity();
    admin.setId(1L);
    admin.setFirstName("John");
    admin.setLastName("Doe");
    admin.setEmail("admin@abv.bg");
    admin.setRoles(Set.of(new UserRoleEntity(UserRoleEnum.ADMIN), new UserRoleEntity(UserRoleEnum.USER)));

    user = new UserEntity();
    user.setId(2L);
    user.setEmail("user@abv.bg");
    user.setRoles(Set.of(new UserRoleEntity(UserRoleEnum.USER)));

    //Mock categories
    cat1 = new Category();
    cat1.setName(CategoryEnum.CAR);
    cat2 = new Category();
    cat2.setName(CategoryEnum.PEDESTRIAN);

    //Mock routes
    route1 = new Route();
    route1.setId(1L);
    route1.setName("testRoute1");
    route1.setAuthor(admin);
    route1.setCategories(Set.of(cat1));
    route1.setPictures(new HashSet<>());

    //Mock picture entity
    Picture picture = new Picture();
    picture.setUrl("testUrl");

    route2 = new Route();
    route2.setId(2L);
    route2.setName("testRoute2");
    route2.setAuthor(user);
    route2.setCategories(Set.of(cat2));
    route2.setPictures(Set.of(picture));

    vm1 = new RouteViewModel();
    vm1.setName("testRoute1");

    vm2 = new RouteViewModel();
    vm2.setName("testRoute2");
  }

  @Test
  void findAllRoutes_withPagination_returnsMappedViewModels() {
    // Arrange
    Pageable pageable = PageRequest.of(0, 2); // first page, 2 items
    List<Route> routes = List.of(route1, route2);
    Page<Route> page = new PageImpl<>(routes, pageable, routes.size());

    Mockito.when(routeRepository.findAll(pageable)).thenReturn(page);
    Mockito.when(modelMapper.map(route1, RouteViewModel.class)).thenReturn(vm1);
    Mockito.when(modelMapper.map(route2, RouteViewModel.class)).thenReturn(vm2);

    // Act
    Page<RouteViewModel> result = serviceToTest.findAllRoutes(pageable, null);

    // Assert
    Assertions.assertEquals(2, result.getContent().size());
    Assertions.assertEquals("testRoute1", result.getContent().get(0).getName());
    Assertions.assertEquals("testRoute2", result.getContent().get(1).getName());

    Mockito.verify(routeRepository).findAll(pageable);
    Mockito.verify(modelMapper).map(route1, RouteViewModel.class);
    Mockito.verify(modelMapper).map(route2, RouteViewModel.class);
  }


  @Test
  void findRouteByIdWithCanModifyProperty_throwsObjNotFound() {
    Assertions.assertThrows(ObjectNotFoundException.class, () ->
            serviceToTest.findRouteByIdWithCanModifyProperty("testEmail", 333L));
  }

  @Test
  void findRouteByIdWithCanModifyProperty_returnsRouteServiceModel() {
    //1. Arrange
    RouteDetailsServiceModel serviceModel = new RouteDetailsServiceModel();
    serviceModel.setName(route1.getName());


    //1.1 Mock dependencies
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));
    Mockito.when(modelMapper.map(route1, RouteDetailsServiceModel.class)).thenReturn(serviceModel);

    //2. Act
    RouteDetailsServiceModel result =
            this.serviceToTest.findRouteByIdWithCanModifyProperty("admin@abv.bg", 1L);

    //3. Assert
    Assertions.assertEquals(serviceModel.getName(), result.getName());
  }

  @Test
  void isAdmin() {
    Mockito.when(userService.findByEmail("admin@abv.bg")).thenReturn(admin);

    boolean result = this.serviceToTest.isAdmin("admin@abv.bg");

    Assertions.assertTrue(result);
  }

  @Test
  void isOwner_throwsObjNotFoundExc() {
    Assertions.assertThrows(ObjectNotFoundException.class,
            () -> this.serviceToTest.isOwner("admin@abv.bg", 999L));
  }

  @Test
  void isOwner_returnsTrue() {
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

    boolean result = this.serviceToTest.isOwner("admin@abv.bg", 1L);

    Assertions.assertTrue(result);
  }

  @Test
  void findRouteByName_routeAlreadyExists() {
    Mockito.when(routeRepository.findByName("testRoute1"))
            .thenReturn(Optional.of(route1));

    boolean result = this.serviceToTest.findRouteByName("testRoute1");

    Assertions.assertFalse(result);
  }

  @Test
  void findRouteByName_routeNotExists() {
    Mockito.when(routeRepository.findByName("testRoute333"))
            .thenReturn(Optional.empty());

    boolean result = this.serviceToTest.findRouteByName("testRoute333");

    Assertions.assertTrue(result);
  }

  @Test
  void findRouteById_returnsOptionalRoute() {
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

    Optional<Route> route = this.serviceToTest.findRouteById(1L);
    Assertions.assertEquals(route1, route.get());
  }

  @Test
  void findRouteById_returnsOptionalEmpty() {
    Mockito.when(routeRepository.findById(222L)).thenReturn(Optional.empty());

    Optional<Route> route = this.serviceToTest.findRouteById(222L);
    Assertions.assertTrue(route.isEmpty());
  }

  @Test
  void deleteRouteById_throwsObjNotFound() {
    Assertions.assertThrows(ObjectNotFoundException.class, () -> this.serviceToTest.deleteRouteById(333L));
  }

  @Test
  void deleteRouteById_deletesRoute() {
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

    this.serviceToTest.deleteRouteById(1L);

    Mockito.verify(routeRepository).delete(route1);
  }

  @Test
  void findMostCommentedRoute_shouldMapAllFieldsCorrectly() {
    // 1. Arrange
    Pageable topOne = PageRequest.of(0, 1);

    RouteDetailsServiceModel expectedMappedModel = new RouteDetailsServiceModel();

    expectedMappedModel.setName("testRoute1");
    expectedMappedModel.setAuthorFullName("John Doe");
    expectedMappedModel.setCategories(Set.of(CategoryEnum.CAR));
    expectedMappedModel.setCanModify(true);

    // Mock repository
    Mockito.when(routeRepository.findMostCommentedRoute(topOne)).thenReturn(List.of(route1));
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));
    Mockito.when(modelMapper.map(route1, RouteDetailsServiceModel.class)).thenReturn(expectedMappedModel);

    // Act
    RouteDetailsServiceModel result = serviceToTest.findMostCommentedRoute(admin.getEmail());

    // Assert
    Assertions.assertEquals("John Doe", result.getAuthorFullName());
    Assertions.assertEquals(Set.of(CategoryEnum.CAR), result.getCategories());
    Assertions.assertTrue(result.isCanModify());
  }

  @Test
  void addNewRoute_returnsIdOfTheNewRoute() throws IOException {
    //1. Arrange
    AddRouteServiceModel serviceModel = new AddRouteServiceModel();
    serviceModel.setAuthorId(admin.getId());
    serviceModel.setName(route1.getName());
    serviceModel.setCategories(route1.getCategories().stream().map(Category::getName).collect(Collectors.toSet()));

    //1.1 Mock dependencies
    Mockito.when(modelMapper.map(serviceModel, Route.class)).thenReturn(route1);
    Mockito.when(userService.findByEmail(admin.getEmail())).thenReturn(admin);
    Mockito.when(categoryService.findByName(CategoryEnum.CAR)).thenReturn(cat1);
    Mockito.when(routeRepository.save(route1)).thenReturn(route1);

    Long resultId = serviceToTest.addNewRoute(serviceModel, admin.getEmail());

    // Assert
    Assertions.assertEquals(1L, resultId);
    Mockito.verify(routeRepository).save(route1);
    Mockito.verify(eventPublisher).publishEvent(Mockito.any(UpdateUserLevelEvent.class));
  }

  @Test
  void isNotOwnerOrIsAdmin_isAdmin() {

    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));
    Mockito.when(userService.findByEmail("admin@abv.bg")).thenReturn(admin);

    boolean result = this.serviceToTest
            .isNotOwnerOrIsAdmin("admin@abv.bg", 1L);

    Assertions.assertTrue(result);
  }

  @Test
  void isNotOwnerOrIsAdmin_isNotOwner() {
    Mockito.when(routeRepository.findById(1L)).thenReturn(Optional.of(route1));

    boolean result = this.serviceToTest
            .isNotOwnerOrIsAdmin("user@abv.bg", 1L);

    Assertions.assertTrue(result);
  }

  @Test
  void findAllByCategory_returnsListOfRouteViewModels() {
    //1.Arrange
    RouteViewModel routeViewModel = new RouteViewModel();
    routeViewModel.setName(route1.getName());

    List<Route> expectedRoutes = List.of(route1);

    //1.1. Mock dependencies
    Mockito.when(categoryService.findByName(CategoryEnum.CAR)).thenReturn(cat1);
    Mockito.when(routeRepository.findAllByCategories(Set.of(cat1))).thenReturn(Optional.of(expectedRoutes));
    Mockito.when(modelMapper.map(route1, RouteViewModel.class)).thenReturn(routeViewModel);

    //2. Act
    List<RouteViewModel> viewModels = this.serviceToTest.findAllByCategory("CAR");

    //3. Assert
    Assertions.assertEquals(expectedRoutes.size(), viewModels.size());
    Assertions.assertEquals("testRoute1", expectedRoutes.get(0).getName());

    Category category = expectedRoutes.get(0).getCategories().stream().findFirst().get();
    Assertions.assertEquals(CategoryEnum.CAR, category.getName());
    Assertions.assertEquals("/images/pic4.jpg", viewModels.getFirst().getPictureUrl());
  }

  @Test
  void findAllByCategory_testIfThereAreImageUrl() {
    RouteViewModel routeViewModel = new RouteViewModel();
    routeViewModel.setName(route2.getName());

    List<Route> expectedRoutes = List.of(route2);

    //1.1. Mock dependencies
    Mockito.when(categoryService.findByName(CategoryEnum.PEDESTRIAN)).thenReturn(cat2);
    Mockito.when(routeRepository.findAllByCategories(Set.of(cat2))).thenReturn(Optional.of(expectedRoutes));
    Mockito.when(modelMapper.map(route2, RouteViewModel.class)).thenReturn(routeViewModel);

    //2. Act
    List<RouteViewModel> viewModels = this.serviceToTest.findAllByCategory("PEDESTRIAN");

    //3. Assert
    Assertions.assertEquals("testUrl", viewModels.getFirst().getPictureUrl());
  }

}
