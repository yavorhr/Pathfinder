package com.example.pathfinder.validation.login;

import com.example.pathfinder.model.binding.UserLoginBindingModel;
import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.modelmapper.ModelMapper;

public class LoginValidator implements ConstraintValidator<ValidLogin, UserLoginBindingModel> {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public LoginValidator(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @Override
  public boolean isValid(UserLoginBindingModel bindingModel, ConstraintValidatorContext context) {

    return this.userService.login(modelMapper.map(bindingModel, UserLoginServiceModel.class));
  }
}
