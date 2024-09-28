package com.example.pathfinder.web;

import com.example.pathfinder.model.service.RouteServiceModel;
import com.example.pathfinder.model.service.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RouteController {
  private final RouteService routeService;

  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  @GetMapping("/routes")
  public String getRoutesPage(Model model) {
    List<RouteViewModel> allRoutes = this.routeService.findAllRoutes();
    model.addAttribute("routes", allRoutes);
    return "routes";
  }

}
