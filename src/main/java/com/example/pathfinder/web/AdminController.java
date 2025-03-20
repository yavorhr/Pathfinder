package com.example.pathfinder.web;

import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.service.AdminNotificationService;
import com.example.pathfinder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
  private final AdminNotificationService notificationService;
  private final UserService userService;

  public AdminController(AdminNotificationService notificationService, UserService userService) {
    this.notificationService = notificationService;
    this.userService = userService;
  }

  @GetMapping("/admin/notifications")
  public String viewNotifications(Model model) {
    model.addAttribute("notifications", notificationService.getNotifications());
    return "notifications";
  }

  @PostMapping("/admin/remove-user/{email}")
  @ResponseBody
  public ResponseEntity<String> deleteUserById(@PathVariable String email) {
    this.userService.deleteUser(email);
    this.notificationService.deleteNotification(email);
    return ResponseEntity.ok("User with email" + email + " has been deleted.");
  }

  @PutMapping("/admin/change-user-access/{username}")
  @ResponseBody
  public ResponseEntity<UserUpdateStatusResponse> changeUserAccess(@PathVariable String username) {
    UserUpdateStatusResponse statusResponse = this.userService.changeAccess(username);

    System.out.println(statusResponse.isEnabled());

    return ResponseEntity.ok(statusResponse);
  }
}
