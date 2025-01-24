package com.example.pathfinder.service.events;

import com.example.pathfinder.model.entity.UserEntity;

public class UpdateUserLevelEvent {
  private UserEntity user;

  public UpdateUserLevelEvent(UserEntity user) {
    this.user = user;
  }

  public UserEntity getUser() {
    return user;
  }

  public UpdateUserLevelEvent setUser(UserEntity user) {
    this.user = user;
    return this;
  }
}
