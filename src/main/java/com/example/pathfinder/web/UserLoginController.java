package com.example.pathfinder.web;

import com.example.pathfinder.model.entity.enums.LoginErrorType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserLoginController {

  @GetMapping("/login")
  public String login(HttpServletRequest request,
                      Model model) {

    LoginErrorType errorType = (LoginErrorType) request.getSession().getAttribute("LOGIN_ERROR_TYPE");

    if (errorType != null) {
      switch (errorType) {
        case ACCOUNT_LOCKED -> model.addAttribute("login_error_message", "Your account is locked. Try again in 15 minutes.");
        case ACCOUNT_DISABLED -> model.addAttribute("login_error_message", "Your account is disabled. Admin will contact you.");
        case INVALID_CREDENTIALS -> model.addAttribute("login_error_message", "Invalid credentials. Please try again.");
        case ACCOUNT_EXPIRED -> model.addAttribute("login_error_message", "Your account has expired.");
        case USER_NOT_FOUND -> model.addAttribute("login_error_message", "None existing email.");
      }
    }

    request.getSession().removeAttribute("LOGIN_ERROR_TYPE");
    String email = request.getParameter("email");

    if (email != null) {
      model.addAttribute("email", email);
    }

    return "login";
  }
}

