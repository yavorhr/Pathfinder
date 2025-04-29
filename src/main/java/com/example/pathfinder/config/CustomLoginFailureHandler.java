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
    System.out.println("Login failure handler triggered for: " + email);

    try {
      UserEntity user = userService.findByEmail(email);

      if (!user.isEnabled()) {
        request.getSession().setAttribute("LOGIN_ERROR_TYPE", LoginErrorType.ACCOUNT_DISABLED);
        response.sendRedirect("/users/login");
        return;
      }
      userService.increaseUserFailedLoginAttempts(user);

    } catch (ObjectNotFoundException e) {
      request.getSession().setAttribute("LOGIN_ERROR_TYPE", LoginErrorType.USER_NOT_FOUND);
      response.sendRedirect("/users/login");
    }

    if (exception instanceof DisabledException) {
      request.getSession().setAttribute("LOGIN_ERROR_TYPE", LoginErrorType.ACCOUNT_DISABLED);
      response.sendRedirect("/users/login");
      return;
    }

    if (exception instanceof LockedException) {
      request.getSession().setAttribute("LOGIN_ERROR_TYPE", LoginErrorType.ACCOUNT_LOCKED);
      response.sendRedirect("/users/login");
      return;
    }

    // Store the error in the session (mimicking flash attribute)
    request.getSession().setAttribute("LOGIN_ERROR_TYPE", LoginErrorType.INVALID_CREDENTIALS);

    response.sendRedirect("/users/login");
  }
}
