package com.example.pathfinder.service.impl.principal;

import com.example.pathfinder.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PathfinderUser extends User {
  private final Long id;
  private final UserEntity userEntity;

  public PathfinderUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
    super(
            userEntity.getEmail(),
            userEntity.getPassword(),
            userEntity.isEnabled(),
            true,
            true,
            !userEntity.isAccountLocked(),
            authorities);
    this.id = userEntity.getId();
    this.userEntity = userEntity;
  }

  public Long getId() {
    return id;
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }
}
