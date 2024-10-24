package com.example.pathfinder.validation.register;

import com.example.pathfinder.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String > {
  private final UserService userService;

  public UniqueEmailValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (email == null) {
      return true;
    }
    return userService.isEmailAvailable(email);
  }
}
