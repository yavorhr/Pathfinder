package com.example.pathfinder.validation.register;

import com.example.pathfinder.model.binding.UserRegisterBindingModel;
import com.example.pathfinder.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.modelmapper.ModelMapper;

public class PasswordsValidator implements ConstraintValidator<DoesPasswordAndConfirmPasswordMatch, UserRegisterBindingModel> {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public PasswordsValidator(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean isValid(UserRegisterBindingModel bindingModel, ConstraintValidatorContext context) {

    return bindingModel.getPassword().equals(bindingModel.getConfirmPassword());
  }
}
