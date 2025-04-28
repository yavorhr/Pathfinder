package com.example.pathfinder.config;

import com.example.pathfinder.model.entity.UserEntity;
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
    String errorMessage = "Invalid credentials. Please try again.";

    if (exception instanceof LockedException) {
      request.getSession().setAttribute("login_error_message", "Your account is locked. Please try again in 15 minutes.");
      response.sendRedirect("/users/login?error=true");
      return;
    }

    if (exception instanceof DisabledException) {
      request.getSession().setAttribute("login_error_message", "Your account is disabled. Admin will contact you further.");
      response.sendRedirect("/users/login?error=true");
      return;
    }


    try {
      UserEntity user = userService.findByEmail(email);
      userService.increaseUserFailedLoginAttempts(user);

    } catch (ObjectNotFoundException e) {
      errorMessage = "User with this email does not exist.";
    }

    // Store the error in the session (mimicking flash attribute)
    request.getSession().setAttribute("login_error_message", errorMessage);

    // Always redirect to login page with error=true
    response.sendRedirect("/users/login?error=true");
  }
}
