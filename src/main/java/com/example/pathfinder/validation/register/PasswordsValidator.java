package com.example.pathfinder.validation.register;

import com.example.pathfinder.model.binding.UserRegisterBindingModel;
import com.example.pathfinder.model.entity.UserEntity;
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
    if (bindingModel.getEmail().isEmpty()) {
      return false;
    }

    UserEntity user = this.userService.findByEmail(bindingModel.getEmail());

    boolean passwordMatch = bindingModel.getPassword().equals(bindingModel.getConfirmPassword());
    boolean validPassword = passwordMatch && user.getPassword().equals(bindingModel.getPassword());

    return passwordMatch && validPassword;
  }
}
