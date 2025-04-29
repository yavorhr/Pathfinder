package com.example.pathfinder.service.schedulers;

import com.example.pathfinder.model.entity.UserEntity;
import com.example.pathfinder.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AccountUnlockScheduler {
  private final UserService userService;

  public AccountUnlockScheduler(UserService userService) {
    this.userService = userService;
  }

  //every 15 minutes
  @Scheduled(fixedRate = 900000 )
  public void unlockExpiredAccounts() {
    List<UserEntity> lockedUsers = userService.findLockedUsers();

    LocalDateTime now = LocalDateTime.now();

    System.out.println("yes");

    for (UserEntity user : lockedUsers) {
      if (user.getLockTime().isBefore(now)) {
        user.setAccountLocked(false);
        user.setFailedLoginAttempts(0);
        userService.updateUser(user);
      }
    }

  }
}
