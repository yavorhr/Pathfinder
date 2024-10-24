package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.RouteAddBindingModel;
import com.example.pathfinder.model.service.RouteServiceModel;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class RouteController {
  private final RouteService routeService;
  private ModelMapper modelMapper;

  public RouteController(RouteService routeService, ModelMapper modelMapper) {
    this.routeService = routeService;
    this.modelMapper = modelMapper;
  }

  @ModelAttribute("routeAddBindingModel")
  public RouteAddBindingModel routeAddBindingModel() {
    return new RouteAddBindingModel();
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

  // ADD
  @GetMapping("/routes/add")
  public String getAddRoutePage() {
    return "add-route";
  }

  @PostMapping("/routes/add")
  public String addRoute(@Valid RouteAddBindingModel routeAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws IOException {

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors().forEach(error -> {
        System.out.println("Error: " + error.getObjectName() +  " - " + error.getDefaultMessage());
      });
      redirectAttributes
              .addFlashAttribute("routeAddBindingModel", routeAddBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel", bindingResult);
      return "redirect:add";
    }

    RouteServiceModel routeServiceModel = this.modelMapper.map(routeAddBindingModel, RouteServiceModel.class);
    routeServiceModel.setGpxCoordinates(new String(routeAddBindingModel.getGpxCoordinates().getBytes()));

    Long id = routeService.addNewRoute(routeServiceModel);

    return "redirect:/routes/details/" + id;
  }
}
