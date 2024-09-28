package com.example.pathfinder.service;

import com.example.pathfinder.model.service.RouteViewModel;

import java.util.List;

public interface RouteService {
  List<RouteViewModel> findAllRoutes();
}
