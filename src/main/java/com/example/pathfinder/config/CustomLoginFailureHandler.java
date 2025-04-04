package com.example.pathfinder.config;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    UserEntity user = userService.findByEmail(email);

    if (user != null) {
      if (userService.isAccountLocked(user)) {
        response.sendRedirect("/users/login?locked=true");
        return;
      } else {
        userService.increaseUserFailedLoginAttempts(user);
      }
    }
    response.sendRedirect("/users/login/error");
  }
}
