package com.example.pathfinder.service.impl.principal;

import com.example.pathfinder.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

public class PathfinderUser extends User {
  private final Long id;
  private final UserEntity userEntity;

  public PathfinderUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
    super(
            userEntity.getEmail(),
            userEntity.getPassword(),
            userEntity.isEnabled(),
            !userEntity.isAccountExpired(),
            true,
            !userEntity.isAccountLocked(),
            authorities);
    this.id = userEntity.getId();
    this.userEntity = userEntity;
  }

  @Override
  public boolean isEnabled() {
    return userEntity.isEnabled();
  }

  @Override
  public boolean isAccountNonExpired() {
    LocalDateTime lastLogin = userEntity.getLastLoginTime();
    if (lastLogin == null) {
      // Never logged in users expire after registration + 1 year
      return userEntity.getRegistrationDate().isAfter(LocalDateTime.now().minusYears(1));
    }
    return lastLogin.isAfter(LocalDateTime.now().minusYears(1));
  }

  public Long getId() {
    return id;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }
}
