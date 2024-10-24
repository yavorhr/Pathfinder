package com.example.pathfinder.web;

import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class RouteController {
  private final RouteService routeService;

  public RouteController(RouteService routeService) {
    this.routeService = routeService;
  }

  // GET
  @GetMapping("/routes")
  public String getRoutesPage(Model model) {
    List<RouteViewModel> allRoutes = this.routeService.findAllRoutes();
    model.addAttribute("routes", allRoutes);

    return "routes";
  }

  @GetMapping("/routes/details/{id}")
  public String getRouteDetailsPage(@PathVariable Long id, Model model) {
    RouteDetailsViewModel route = this.routeService.findById(id);
    model.addAttribute("routeDetails", route);

    return "route-details";
  }

  @GetMapping("/routes/add")
  public  String getAddRoutePage(){

    return "add-route";
  }
}
