package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.UserService;
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

@ExtendWith(MockitoExtension.class)
public class RouteServiceImplTest {
  @InjectMocks
  private RouteServiceImpl serviceToTest;
  private Route route1, route2;
  private RouteViewModel vm1, vm2;

  @Mock
  private RouteRepository routeRepository;

  @Mock
  private ModelMapper modelMapper;

  @Mock
  private UserService userService;

  @Mock
  private CategoryService categoryService;

  @Mock
  private ApplicationEventPublisher eventPublisher;

  @BeforeEach
  void setUp() {

    route1 = new Route();
    route1.setId(1L);
    route1.setName("testRoute1");

    route2 = new Route();
    route2.setId(2L);
    route2.setName("testRoute2");

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
}
