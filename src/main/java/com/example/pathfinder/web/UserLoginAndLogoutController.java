package com.example.pathfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserLoginAndLogoutController {

  public UserLoginAndLogoutController() {
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }
}
