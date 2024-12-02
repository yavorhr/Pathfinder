package com.example.pathfinder.service;

import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.view.RouteViewModel;
import java.util.List;
import java.util.Optional;

public interface RouteService {
  List<RouteViewModel> findAllRoutes();

  Optional<Route> findById(Long id);

  Long addNewRoute(AddRouteServiceModel routeServiceModel);

  boolean findRouteByName(String name);
}
