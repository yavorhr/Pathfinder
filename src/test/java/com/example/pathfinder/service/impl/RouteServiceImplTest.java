package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;

public class RouteServiceImplTest {
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private RouteRepository routeRepository;
  @Mock
  private UserService userService;
  @Mock
  private ApplicationEventPublisher eventPublisher;

  @InjectMocks
  private RouteServiceImpl serviceToTest;

  private Route route1, route2;
  private RouteViewModel vm1, vm2;

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
}
