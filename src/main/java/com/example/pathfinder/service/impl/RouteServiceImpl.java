package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.service.RouteDetailsServiceModel;
import com.example.pathfinder.model.view.RouteDetailsViewModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
  private final RouteRepository routeRepository;
  private final ModelMapper modelMapper;
  private final UserService userService;
  private final CategoryService categoryService;

  public RouteServiceImpl(RouteRepository repository, ModelMapper modelMapper, UserService userService, CategoryService categoryService) {
    this.routeRepository = repository;
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.categoryService = categoryService;
  }

  @Override
  public List<RouteViewModel> findAllRoutes() {
    return this.routeRepository.findAll()
            .stream()
            .map(e -> {
              RouteViewModel viewModel = this.modelMapper.map(e, RouteViewModel.class);

              viewModel.setPictureUrl(e.getPictures().isEmpty() ? "/images/pic4" : e.getPictures().stream().findAny().get().getUrl());

              return viewModel;
            })
            .collect(Collectors.toList());
  }

  @Override
  public Optional<Route> findRouteById(Long id) {
    return this.routeRepository.findById(id);
  }

  @Override
  public RouteDetailsServiceModel findRouteByIdWithCanModifyProperty(String email, Long id) {

    var route =
            this.routeRepository
                    .findById(id)
                    .orElseThrow(() ->
                            new ObjectNotFoundException("Route with Id: " + id + " is not found!"));

    RouteDetailsServiceModel serviceModel =
            this.modelMapper.map(route, RouteDetailsServiceModel.class);

    serviceModel.setCanModify(isOwner(email, id));

    return serviceModel;
  }

  @Override
  public Long addNewRoute(AddRouteServiceModel routeServiceModel) {
    Route route = this.modelMapper.map(routeServiceModel, Route.class);

    route.setAuthor(this.userService.findById(routeServiceModel.getAuthorId()).get());
    route.setCategories(mapCategories(routeServiceModel.getCategories()));

    route = this.routeRepository.save(route);

    return route.getId();
  }

  @Override
  public boolean findRouteByName(String name) {
    return this.routeRepository.findByName(name).isEmpty();
  }

  @Override
  public boolean isOwner(String authorEmail, Long routeId) {
    var route = routeRepository.
            findById(routeId).
            orElseThrow(() -> new ObjectNotFoundException("Route with id " + routeId + " not found!"));

    var author = userService.
            findByEmail(authorEmail).
            orElseThrow(() -> new ObjectNotFoundException("User with email " + authorEmail + " not found!"));

    return isAdmin(author) ||
            route.getAuthor().getEmail().equals(authorEmail);
  }

  private boolean isAdmin(UserEntity user) {
    return user.
            getRoles().
            stream().
            map(UserRoleEntity::getRole).
            anyMatch(r -> r == UserRoleEnum.ADMIN);
  }

  // Helpers
  private Set<Category> mapCategories(Set<CategoryEnum> categoryEnums) {
    Set<Category> categoriesSet = new HashSet<>();

    categoryEnums.forEach(catEnum -> {
      Category entity = this.categoryService.findByName(catEnum).get();
      categoriesSet.add(entity);
    });

    return categoriesSet;
  }
}
