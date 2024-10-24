package com.example.pathfinder.validation.route;

import com.example.pathfinder.service.RouteService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueRouteNameValidator implements ConstraintValidator<UniqueRouteName, String > {
  private RouteService routeService;

  public UniqueRouteNameValidator(RouteService routeService) {
    this.routeService = routeService;
  }

  @Override
  public boolean isValid(String name, ConstraintValidatorContext context) {
    return this.routeService.findRouteByName(name);
  }
}
