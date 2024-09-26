package com.example.pathfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginAndLogoutController {

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }
}
