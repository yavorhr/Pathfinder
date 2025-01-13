package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.RouteAddBindingModel;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

  @GetMapping("/routes/{category}")
  public String getRoutesPerCategory(@PathVariable String category, Model model) {
    List<RouteViewModel> allRoutes = this.routeService.findAllByCategory(category);
    model.addAttribute("routes", allRoutes);
    model.addAttribute("category", category);

    return "route-category";
  }

  @GetMapping("/routes/details/{id}")
  public String getRouteDetailsPage(@PathVariable Long id, Model model,
                                    @AuthenticationPrincipal UserDetails principal) {
    RouteDetailsViewModel route =
            this.modelMapper.map(this.routeService.findRouteByIdWithCanModifyProperty(principal.getUsername(), id),
                    RouteDetailsViewModel.class);

    model.addAttribute("routeDetails", route);
    return "route-details";
  }

  @GetMapping("/routes/most-commented")
  public String getMostCommentedRoute(Model model) {
    RouteDetailsViewModel viewModel = this.modelMapper.map(this.routeService.findMostCommentedRoute(), RouteDetailsViewModel.class);
    model.addAttribute("routeDetails", viewModel);

    return "route-details";
  }

  // ADD
  @GetMapping("/routes/add")
  public String getAddRoutePage() {

    //TODO With @PreAuthorize
//    if (!currentUser.isLoggedIn()) {
//      return "redirect:/users/login";
//    }

    return "add-route";
  }

  @PostMapping("/routes/add")
  public String addRoute(@Valid RouteAddBindingModel routeAddBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) throws IOException {

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("routeAddBindingModel", routeAddBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.routeAddBindingModel", bindingResult);
      return "redirect:add";
    }

    AddRouteServiceModel routeServiceModel = this.modelMapper.map(routeAddBindingModel, AddRouteServiceModel.class);
    routeServiceModel.setGpxCoordinates(new String(routeAddBindingModel.getGpxCoordinates().getBytes()));
//    routeServiceModel.setAuthorId(this.currentUser.getId());

    Long id = routeService.addNewRoute(routeServiceModel);

    return "redirect:/routes/details/" + id;
  }
}
