package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.Category;
import com.example.pathfinder.model.entity.Route;
import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.CategoryEnum;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.model.service.AddRouteServiceModel;
import com.example.pathfinder.model.service.RouteDetailsServiceModel;
import com.example.pathfinder.model.view.RouteViewModel;
import com.example.pathfinder.repository.RouteRepository;
import com.example.pathfinder.service.CategoryService;
import com.example.pathfinder.service.RouteService;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.service.events.UpdateUserLevelEvent;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
  private final ApplicationEventPublisher eventPublisher;

  public RouteServiceImpl(RouteRepository repository, ModelMapper modelMapper, UserService userService, CategoryService categoryService, ApplicationEventPublisher eventPublisher) {
    this.routeRepository = repository;
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.categoryService = categoryService;
    this.eventPublisher = eventPublisher;
  }

  @Override
  public List<RouteViewModel> findAllRoutes() {
    return this.routeRepository.findAll()
            .stream()
            .map(this::mapToViewModel)
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

    Set<CategoryEnum> categoryEnums = route.getCategories()
            .stream()
            .map(Category::getName)
            .collect(Collectors.toSet());

    serviceModel.setCategories(categoryEnums);
    serviceModel.setCanModify(isOwnerOrIsAdmin(email, id));
    serviceModel.setAuthorFullName(route.getAuthor().getFirstName() + " " + route.getAuthor().getLastName());

    return serviceModel;
  }

  @Override
  public Long addNewRoute(AddRouteServiceModel routeServiceModel, String email) {
    Route route = this.modelMapper.map(routeServiceModel, Route.class);

    var author = this.userService.findByEmail(email);

    route.setAuthor(author);
    route.setCategories(mapCategories(routeServiceModel.getCategories()));

    route = this.routeRepository.save(route);

    eventPublisher.publishEvent(new UpdateUserLevelEvent(author));

    return route.getId();
  }

  @Override
  public boolean findRouteByName(String name) {
    return this.routeRepository.findByName(name).isEmpty();
  }

  @Override
  public boolean isOwnerOrIsAdmin(String authorEmail, Long routeId) {
    return isOwner(authorEmail, routeId) || isAdmin(authorEmail);
  }

  @Override
  public boolean isNotOwnerOrIsAdmin(String authorEmail, Long routeId) {
    return !isOwner(authorEmail, routeId) || isAdmin(authorEmail);
  }

  @Override
  public boolean isOwner(String authorEmail, Long routeId) {
    var route = routeRepository.
            findById(routeId).
            orElseThrow(() -> new ObjectNotFoundException("Route with id " + routeId + " not found!"));

    return route.getAuthor().getEmail().equals(authorEmail);
  }


  @Override
  public List<RouteViewModel> findAllByCategory(String category) {
    Category categoryEntity = this.categoryService
            .findByName(CategoryEnum.valueOf(category.toUpperCase()))
            .orElseThrow(() -> new ObjectNotFoundException("Category with name " + category + " was not found!"));

    List<Route> routes = this.routeRepository
            .findAllByCategories(Set.of(categoryEntity))
            .orElseThrow(() -> new ObjectNotFoundException("No routes found in category : " + category));

    return routes
            .stream()
            .map(this::mapToViewModel)
            .collect(Collectors.toList());
  }

  @Override
  public RouteDetailsServiceModel findMostCommentedRoute(String email) {
    Pageable topOne = PageRequest.of(0, 1); // Fetch only the first result
    Route mostCommentedRoute = routeRepository.findMostCommentedRoute(topOne).get(0);

    var routeDetailsServiceModel = this.modelMapper.map(mostCommentedRoute, RouteDetailsServiceModel.class);
    routeDetailsServiceModel.setCanModify(isOwnerOrIsAdmin(email, mostCommentedRoute.getId()));

    return routeDetailsServiceModel;
  }

  @Override
  public void deleteRouteById(Long routeId) {
    Route route =
            this.routeRepository
                    .findById(routeId)
                    .orElseThrow(() -> new ObjectNotFoundException("Route with id " + routeId + " was not found!"));

    this.routeRepository.delete(route);
  }

  // Helpers
  private boolean isAdmin(String authorEmail) {
    var user = userService.findByEmail(authorEmail);

    return user.
            getRoles().
            stream().
            map(UserRoleEntity::getRole).
            anyMatch(r -> r == UserRoleEnum.ADMIN);
  }

  private Set<Category> mapCategories(Set<CategoryEnum> categoryEnums) {
    Set<Category> categoriesSet = new HashSet<>();

    categoryEnums.forEach(catEnum -> {
      Category entity = this.categoryService.findByName(catEnum).get();
      categoriesSet.add(entity);
    });

    return categoriesSet;
  }

  private RouteViewModel mapToViewModel(Route r) {
    RouteViewModel viewModel = this.modelMapper.map(r, RouteViewModel.class);

    viewModel.setPictureUrl(r.getPictures().isEmpty()
            ? "/images/pic4"
            : r.getPictures().stream().findAny().get().getUrl());

    return viewModel;
  }
}
