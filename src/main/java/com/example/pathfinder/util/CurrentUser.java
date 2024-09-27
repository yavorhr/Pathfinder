package com.example.pathfinder.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class CurrentUser {
  private Long id;
  private String username;

  public CurrentUser() {
  }

  public String getUsername() {
    return username;
  }

  public CurrentUser setUsername(String username) {
    this.username = username;
    return this;
  }

  public Long getId() {
    return id;
  }

  public CurrentUser setId(Long id) {
    this.id = id;
    return this;
  }

  public void clear() {
    this.id = null;
    this.username = "";
  }

  public void login(Long id, String username) {
    this.setUsername(username);
    this.setId(id);
  }

  public boolean isLoggedIn(){
    return this.id != null;
  }
}
