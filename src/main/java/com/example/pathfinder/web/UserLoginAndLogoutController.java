package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.UserLoginBindingModel;
import com.example.pathfinder.model.service.UserLoginServiceModel;
import com.example.pathfinder.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginAndLogoutController {
  private final UserService userService;

  public UserLoginAndLogoutController(UserService userService) {
    this.userService = userService;
  }

  @ModelAttribute("userLoginBindingModel")
  public UserLoginBindingModel userLoginBindingModel() {
    return new UserLoginBindingModel();
  }

  @GetMapping("/login")
  public String loginPage() {
    return "login";
  }

  @PostMapping("/login")
  public String loginConfirm(@Valid UserLoginBindingModel userLoginBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

    UserLoginServiceModel user = userService.
            findUserByUserNameAndPassword
                    (userLoginBindingModel.getUsername(), userLoginBindingModel.getPassword());

    if (user == null) {
      redirectAttributes
              .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
              .addFlashAttribute("org.springframework.validation.userLoginBindingModel", bindingResult)
              .addFlashAttribute("userNotExists", true);
      return "redirect:login";
    }

    if (bindingResult.hasErrors()) {
      redirectAttributes
              .addFlashAttribute("userLoginBindingModel", userLoginBindingModel)
              .addFlashAttribute("org.springframework.validation.userLoginBindingModel", bindingResult);
      return "redirect:login";
    }

    this.userService.loginUser(user.getId(), user.getUsername());

    return "index";
  }

  @GetMapping("/logout")
  public String logout() {
    this.userService.logout();
    return "redirect:/";
  }
}
