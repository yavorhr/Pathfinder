package com.example.pathfinder.validation.update;

import com.example.pathfinder.model.binding.ProfileUpdateBindingModel;

import com.example.pathfinder.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUsernameByUpdateValidator implements ConstraintValidator<UniqueUsernameByUserUpdate, ProfileUpdateBindingModel> {
  private final UserService userService;

  public UniqueUsernameByUpdateValidator(UserService userService) {
    this.userService = userService;
  }

  @Override
  public boolean isValid(ProfileUpdateBindingModel bindingModel, ConstraintValidatorContext context) {
    if (usernameNotChangedByUpdate(bindingModel)
            || this.userService.isUsernameAvailable(bindingModel.getUsername())) {
      return true;
    }

    return false;
  }

  // Helper
  private boolean usernameNotChangedByUpdate(ProfileUpdateBindingModel bindingModel) {
    return bindingModel.getUsername().equals(bindingModel.getOriginalUsername());
  }
}
