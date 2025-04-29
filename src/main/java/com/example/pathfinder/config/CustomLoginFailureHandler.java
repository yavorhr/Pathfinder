package com.example.pathfinder.config;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.entity.enums.LoginErrorType;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {
  private final UserService userService;

  public CustomLoginFailureHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    String email = request.getParameter("email");
    String errorType = LoginErrorType.INVALID_CREDENTIALS.name(); // default

    System.out.println("Login failure handler triggered for: " + email);

    try {
      UserEntity user = userService.findByEmail(email);

      if (!user.isEnabled()) {
        errorType = LoginErrorType.ACCOUNT_DISABLED.name();
      } else if (exception instanceof LockedException) {
        errorType = LoginErrorType.ACCOUNT_LOCKED.name();
      }

      userService.increaseUserFailedLoginAttempts(user);

    } catch (ObjectNotFoundException e) {
      errorType = LoginErrorType.USER_NOT_FOUND.name();
    }

    response.sendRedirect("/users/login?errorType=" + errorType);
  }
}
