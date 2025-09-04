package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.RouteAddBindingModel;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.service.RouteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/routes")
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
  @GetMapping
  public String getRoutesPage(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "3") int size,
          @RequestParam(required = false) String keyword,
          Model model) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
    Page<RouteViewModel> routesPage = routeService.findAllRoutes(pageable, keyword);

    model.addAttribute("routesPage", routesPage);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", routesPage.getTotalPages());
    model.addAttribute("keyword", keyword);

    return "routes";
  }

  @GetMapping("/{category}")
  public String getRoutesPerCategory(@PathVariable String category,
                                     Model model) {

    List<RouteViewModel> allRoutes = this.routeService.findAllByCategory(category);

    model.addAttribute("routes", allRoutes);
    model.addAttribute("category", category);
    model.addAttribute("quote", getQuoteForCategory(category));

    return "route-category";
  }

  @GetMapping("/details/{id}")
  public String getRouteDetailsPage(@AuthenticationPrincipal UserDetails principal,
                                    @PathVariable Long id,
                                    Model model) {
    RouteDetailsViewModel route =
            this.modelMapper.map(
                    this.routeService.findRouteByIdWithCanModifyProperty(principal.getUsername(), id),
                    RouteDetailsViewModel.class);

    model.addAttribute("routeDetails", route);
    return "route-details";
  }

  @GetMapping("/most-commented")
  public String getMostCommentedRoute(@AuthenticationPrincipal UserDetails principal,
                                      Model model) {
    var viewModel = this.modelMapper.map(
            this.routeService.findMostCommentedRoute(principal.getUsername()),
            RouteDetailsViewModel.class);

    model.addAttribute("routeDetails", viewModel);

    return "route-details";
  }

  // ADD
  @GetMapping("/add")
  public String getAddRoutePage() {
    return "add-route";
  }

  @PostMapping("/add")
  public String addRoute(@AuthenticationPrincipal UserDetails principal,
                         @Valid RouteAddBindingModel routeAddBindingModel,
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

    Long id = routeService.addNewRoute(routeServiceModel, principal.getUsername());

    return "redirect:/routes/details/" + id;
  }

  @PreAuthorize("@routeServiceImpl.isOwnerOrIsAdmin(#principal.username, #routeId)")
  @DeleteMapping("/delete")
  public String deleteRoute(@RequestParam Long routeId, @AuthenticationPrincipal UserDetails principal) {

    this.routeService.deleteRouteById(routeId);

    return "redirect:/routes";
  }

  // Helpers
  private String getQuoteForCategory(String category) {

    Map<String, List<String>> quotesMap = Map.of(
            "bicycle", List.of(
                    "Life is like riding a bicycle – to keep your balance, you must keep moving.",
                    "Two wheels move the soul.",
                    "Ride as much or as little, as long or as short as you feel. But ride."
            ),
            "pedestrian", List.of(
                    "All truly great thoughts are conceived while walking.",
                    "An early-morning walk is a blessing for the whole day.",
                    "The journey of a thousand miles begins with a single step."
            ),
            "car", List.of(
                    "It's not just a car, it's a way of life.",
                    "Driving isn't just about going from A to B — it's about the ride.",
                    "Fuel your soul with horsepower."
            ),
            "motorcycle", List.of(
                    "It's not just a motorcycle, it's a way of life.",
                    "Driving isn't just about going from A to B — it's about the ride.",
                    "Fuel your soul with horsepower."
            )
    );

    List<String> quotes = quotesMap.getOrDefault(category.toLowerCase(), List.of(
            "Adventure is out there.",
            "Every journey begins with curiosity."
    ));

    return quotes.get(new Random().nextInt(quotes.size()));
  }
}




