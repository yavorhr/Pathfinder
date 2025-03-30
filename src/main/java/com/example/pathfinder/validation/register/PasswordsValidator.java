package com.example.pathfinder.validation.register;

import com.example.pathfinder.model.binding.UserRegisterBindingModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsValidator implements ConstraintValidator<DoesPasswordAndConfirmPasswordMatch, UserRegisterBindingModel> {

  public PasswordsValidator() {
  }

  @Override
  public boolean isValid(UserRegisterBindingModel bindingModel, ConstraintValidatorContext context) {
    if (bindingModel.getEmail().isEmpty()) {
      return false;
    }

    return bindingModel.getPassword().equals(bindingModel.getConfirmPassword());
  }
}
