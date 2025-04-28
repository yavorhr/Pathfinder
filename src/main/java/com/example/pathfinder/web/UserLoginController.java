package com.example.pathfinder.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserLoginController {

  @GetMapping("/login")
  public String login(@RequestParam(value = "locked", required = false) String locked,
                      @RequestParam(value = "error", required = false) String error,
                      HttpServletRequest request,
                      Model model) {
    
    if ("true".equals(error) || "true".equals(locked)) {

      HttpSession session = request.getSession(false);

      if (session != null) {
        String errorMessage = (String) session.getAttribute("login_error_message");
        if (errorMessage != null) {
          model.addAttribute("error_message", errorMessage);
          session.removeAttribute("login_error_message");
        }
      }

      String email = request.getParameter("email");

      if (email != null) {
        model.addAttribute("email", email);
      }
    }

    return "login";
  }
}

