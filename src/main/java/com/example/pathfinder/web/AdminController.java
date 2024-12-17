package com.example.pathfinder.web;

import com.example.pathfinder.service.events.AdminNotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
  private final AdminNotificationService notificationService;

  public AdminController(AdminNotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping("/admin/notifications")
  public String viewNotifications(Model model) {
    model.addAttribute("notifications", notificationService.getNotifications());
    return "notifications";
  }
}
