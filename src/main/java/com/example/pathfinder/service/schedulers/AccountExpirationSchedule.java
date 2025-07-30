package com.example.pathfinder.service.schedulers;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccountExpirationSchedule {

  private final UserService userService;

  public AccountExpirationSchedule(UserService userService) {
    this.userService = userService;
  }

  @Scheduled(cron = "0 0 2 * * *") // Every day at 2 AM
  public void expireInactiveAccounts() {

    LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
    List<UserEntity> inactiveUsers = userService.findInactiveUsersSince(oneYearAgo);

    for (UserEntity user : inactiveUsers) {
      user.setAccountExpired(true);
      userService.updateUser(user);
    }
  }

}
