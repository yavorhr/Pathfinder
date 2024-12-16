package com.example.pathfinder.web;

import com.example.pathfinder.service.events.AdminNotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
  private final AdminNotificationService notificationService;

  public AdminController(AdminNotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping("/admin/notifications")
  public String viewNotifications(Model model) {
    model.addAttribute("notifications", notificationService.getNotifications());
    return "admin-notifications";
  }

  @PostMapping("/admin/clear-notifications")
  public String clearNotifications() {
    notificationService.clearNotifications();
    //TODO: with JS
    return "redirect:/admin-notifications";
  }
}
