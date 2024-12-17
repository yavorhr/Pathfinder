package com.example.pathfinder.web;

import com.example.pathfinder.service.AdminNotificationService;
import com.example.pathfinder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
  private final AdminNotificationService notificationService;
  private final UserService userService;
  private final AdminNotificationService adminNotificationService;

  public AdminController(AdminNotificationService notificationService, UserService userService, AdminNotificationService adminNotificationService) {
    this.notificationService = notificationService;
    this.userService = userService;
    this.adminNotificationService = adminNotificationService;
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
}
