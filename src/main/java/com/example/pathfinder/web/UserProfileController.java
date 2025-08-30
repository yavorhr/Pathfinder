package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.ProfileUpdateBindingModel;
import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.model.service.UserProfileServiceModel;
import com.example.pathfinder.model.view.UserProfileViewModel;
import com.example.pathfinder.service.UserService;
import com.example.pathfinder.util.cloudinary.CloudinaryService;
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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserProfileController {
  private final UserService userService;
  private final ModelMapper modelMapper;
  private final CloudinaryService cloudinaryService;

  public UserProfileController(UserService userService, ModelMapper modelMapper, CloudinaryService cloudinaryService) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.cloudinaryService = cloudinaryService;
  }

  @GetMapping("/profile")
  public String profilePage(@AuthenticationPrincipal UserDetails principal,
                            Model model) {

    UserEntity userEntity = this.userService.findByEmail(principal.getUsername());

    UserProfileViewModel userViewModel =
            this.modelMapper.map(userEntity, UserProfileViewModel.class);

    model.addAttribute("userViewModel", userViewModel);

    return "profile";
  }

  @PatchMapping("/profile/edit")
  public ResponseEntity<?> updateProfile(
          @RequestBody @Valid ProfileUpdateBindingModel bindingModel,
          BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {

      Map<String, String> errors = new HashMap<>(bindingResult.getFieldErrors()
              .stream()
              .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

      return ResponseEntity.badRequest().body(errors);
    }

    UserProfileServiceModel serviceModel = modelMapper.map(bindingModel, UserProfileServiceModel.class);

    UserProfileViewModel userProfileViewModel = this.userService.updateUserData(serviceModel);

    return ResponseEntity.ok(userProfileViewModel);
  }
}


