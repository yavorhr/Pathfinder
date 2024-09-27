package com.example.pathfinder.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileController {

  @GetMapping("/users/profile/{id}")
  public String profilePage(@PathVariable Long id) {
    return "profile";
  }

}

