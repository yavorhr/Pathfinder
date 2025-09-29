package com.example.pathfinder.validation.register;

import com.example.pathfinder.model.binding.UserRegisterBindingModel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsValidator implements ConstraintValidator<DoesPasswordAndConfirmPasswordMatch, UserRegisterBindingModel> {

  public PasswordsValidator() {
  }

  @Override
  public boolean isValid(UserRegisterBindingModel bindingModel, ConstraintValidatorContext context) {
    String password = bindingModel.getPassword();
    String confirmPassword = bindingModel.getConfirmPassword();

    if (password == null || confirmPassword == null || password.isBlank() || confirmPassword.isBlank()) {
      return true;
    }

    if (password.length() < 5 || password.length() > 20
            || confirmPassword.length() < 5 || confirmPassword.length() > 20) {
      return true;
    }

    boolean matches = password.equals(confirmPassword);

    if (!matches) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Passwords don't match")
              .addPropertyNode("confirmPassword")
              .addConstraintViolation();
    }

    return matches;
  }
}