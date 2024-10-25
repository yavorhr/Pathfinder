package com.example.pathfinder.web;

import com.example.pathfinder.model.view.UserProfileViewModel;
import com.example.pathfinder.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserProfileController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserProfileController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/users/profile/{id}")
  public String profilePage(@PathVariable Long id, Model model) {

    UserProfileViewModel userViewModel =
            this.modelMapper.map(this.userService.findUserServiceById(id), UserProfileViewModel.class);

    model.addAttribute("userViewModel", userViewModel);

    return "profile";
  }
}

