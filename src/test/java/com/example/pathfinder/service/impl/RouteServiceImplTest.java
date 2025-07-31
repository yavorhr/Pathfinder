package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.RouteDetailsServiceModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.repository.UserRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.junit.Assert;
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

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class RouteServiceImplTest {
  private Route route1, route2;
  private RouteViewModel vm1, vm2;
  private UserEntity admin, user;

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
    admin.setEmail("admin@abv.bg");
    admin.setRoles(Set.of(new UserRoleEntity(UserRoleEnum.ADMIN), new UserRoleEntity(UserRoleEnum.USER)));

    user = new UserEntity();
    user.setId(1L);
    user.setEmail("admin@abv.bg");
    user.setRoles(Set.of(new UserRoleEntity(UserRoleEnum.ADMIN), new UserRoleEntity(UserRoleEnum.USER)));

    //Mock routes
    route1 = new Route();
    route1.setId(1L);
    route1.setName("testRoute1");
    route1.setAuthor(admin);

    route2 = new Route();
    route2.setId(2L);
    route2.setName("testRoute2");
    route2.setAuthor(user);

    vm1 = new RouteViewModel();
    vm1.setName("testRoute1");

    vm2 = new RouteViewModel();
    vm2.setName("testRoute2");
  }

  @Test
  void findAllRoutes_returnsMappedViewModels() {
    //1. Arrange
    List<Route> routes = List.of(route1, route2);
    Mockito.when(routeRepository.findAll()).thenReturn(routes);
    Mockito.when(modelMapper.map(route1, RouteViewModel.class)).thenReturn(vm1);
    Mockito.when(modelMapper.map(route2, RouteViewModel.class)).thenReturn(vm2);

    // Act
    List<RouteViewModel> result = serviceToTest.findAllRoutes();

    // Assert
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("testRoute1", result.get(0).getName());
    Assertions.assertEquals("testRoute2", result.get(1).getName());

    Mockito.verify(routeRepository).findAll();
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


}
