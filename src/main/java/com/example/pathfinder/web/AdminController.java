package com.example.pathfinder.web;

import com.example.pathfinder.model.binding.RoleUpdateRequest;
import com.example.pathfinder.model.common.UserUpdateStatusResponse;
import com.example.pathfinder.model.view.UserNotificationViewModel;
import com.example.pathfinder.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
  private final UserService userService;

  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/admin/notifications")
  public String viewNotifications(Model model) {
    List<UserNotificationViewModel> users = this.userService.findAllUsers();

    model.addAttribute("users", users );
    return "notifications";
  }

  @PostMapping("/admin/remove-user/{email}")
  @ResponseBody
  public ResponseEntity<String> deleteUserById(@PathVariable String email) {
    this.userService.deleteUser(email);
//    this.notificationService.deleteNotification(email);
    return ResponseEntity.ok("User with email" + email + " has been deleted.");
  }

  @PutMapping("/admin/change-user-access/{username}")
  @ResponseBody
  public ResponseEntity<UserUpdateStatusResponse> changeUserAccess(@PathVariable String username) {
    UserUpdateStatusResponse statusResponse = this.userService.changeAccess(username);

    System.out.println(statusResponse.isEnabled());

    return ResponseEntity.ok(statusResponse);
  }

  @PatchMapping("/admin/api/update-roles")
  @ResponseBody
  public ResponseEntity<?> updateRoles(@RequestBody RoleUpdateRequest request) {

    this.userService.updateUserRoles(request.getUsername(), request.getRoles());

    return ResponseEntity.ok("Roles updated successfully");
  }
}
