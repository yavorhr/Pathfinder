package com.example.pathfinder.web;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserLoginController {

  @GetMapping("/login")
  public String login(@RequestParam(value = "locked", required = false) String locked, Model model) {
    if (locked != null && locked.equals("true")){
      model.addAttribute("locked", true);
    }
    return "login";
  }

  @PostMapping("/users/login-error")
  public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
                            RedirectAttributes attributes) {

    attributes.addFlashAttribute("bad_credentials", true);
    attributes.addFlashAttribute("username", username);

    return "redirect:/users/login";
  }
}
