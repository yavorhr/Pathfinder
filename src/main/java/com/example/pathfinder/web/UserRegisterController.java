package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.UserRegisterBindingModel;
import com.example.pathfinder.model.service.UserRegisterServiceModel;
import com.example.pathfinder.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/users")
public class UserRegisterController {
  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserRegisterController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @ModelAttribute("userRegisterBindingModel")
  public UserRegisterBindingModel userRegisterBindingModel() {
    return new UserRegisterBindingModel();
  }

  @GetMapping("/register")
  public String registerPage() {
    return "register";
  }

  @PostMapping("/register")
  public String registerPage(@Valid UserRegisterBindingModel userRegisterBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

    if (bindingResult.hasErrors()) {

      redirectAttributes
              .addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel)
              .addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);

      return "redirect:register";
}

  UserRegisterServiceModel serviceModel =
          modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);

  convertLocalDate(userRegisterBindingModel.getYear(),
            userRegisterBindingModel.getMonth(),
                    userRegisterBindingModel.getDay(),
                    serviceModel);

                    this.userService.registerUser(serviceModel);

                    return "redirect:login";
                    }

// Helpers
private void convertLocalDate(String year, String month, String day, UserRegisterServiceModel serviceModel) {
    int yearInt = Integer.parseInt(year);
    int monthInt = Integer.parseInt(month);
    int dayInt = Integer.parseInt(day);

    serviceModel.setBirthday(LocalDate.of(yearInt, monthInt, dayInt));
  }
}