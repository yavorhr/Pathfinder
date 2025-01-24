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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.stream.Collectors;

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
  public ResponseEntity<?> updateProfile(
          @RequestBody @Valid ProfileUpdateBindingModel bindingModel,
          BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {

      Map<String, String> errors = bindingResult.getFieldErrors()
              .stream()
              .collect(Collectors.toMap(
                      FieldError::getField,
                      FieldError::getDefaultMessage
              ));

      return ResponseEntity.badRequest().body(errors);
    }

    //update profile
//    return ResponseEntity.ok(UserProfileViewModel)
    System.out.println(bindingModel.getId());
    return null;

  }
}

