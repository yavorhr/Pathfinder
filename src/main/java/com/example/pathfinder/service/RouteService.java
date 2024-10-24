package com.example.pathfinder.service;

import com.example.pathfinder.model.service.RouteServiceModel;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;

import java.util.List;

public interface RouteService {
  List<RouteViewModel> findAllRoutes();

  RouteDetailsViewModel findById(Long id);

  Long addNewRoute(RouteServiceModel routeServiceModel);

  boolean findRouteByName(String name);
}
