package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.ProfileUpdateBindingModel;
import com.example.pathfinder.model.view.UserProfileViewModel;
import com.example.pathfinder.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserProfileController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserProfileController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/users/profile")
  public String profilePage(@AuthenticationPrincipal UserDetails principal,
                            Model model) {

    UserProfileViewModel userViewModel =
            this.modelMapper.map(this.userService.findByEmail(principal.getUsername()),
                    UserProfileViewModel.class);

    model.addAttribute("userViewModel", userViewModel);

    return "profile";
  }


  @PostMapping("/users/profile/edit")
  public ResponseEntity<UserProfileViewModel> updateProfile(
          @RequestBody @Valid ProfileUpdateBindingModel bindingModel) {

    System.out.println(bindingModel.getId());
    return null;

  }
}

