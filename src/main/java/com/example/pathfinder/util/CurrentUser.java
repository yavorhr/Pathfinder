package com.example.pathfinder.util;

import com.example.pathfinder.model.entity.User;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashSet;
import java.util.Set;

@Component
@SessionScope
public class CurrentUser {
  private Long id;
  private String username;
  private boolean isLoggedIn;
  private String fullName;
  private String email;
  private Set<UserRoleEnum> roles;

  public CurrentUser() {
    this.isLoggedIn = false;
    this.roles = new HashSet<>();
  }

  public Set<UserRoleEnum> getRoles() {
    return roles;
  }

  public String getEmail() {
    return email;
  }

  public String getFullName() {
    return fullName;
  }

  public String getUsername() {
    return username;
  }

  public boolean isLoggedIn() {
    return this.id != null;
  }

  public Long getId() {
    return id;
  }

  public CurrentUser setUsername(String username) {
    this.username = username;
    return this;
  }

  public CurrentUser setId(Long id) {
    this.id = id;
    return this;
  }

  public CurrentUser setLoggedIn(boolean loggedIn) {
    isLoggedIn = loggedIn;
    return this;
  }

  public void clear() {
    this.id = null;
    this.username = "";
    this.email = "";
    this.fullName = "";
    this.isLoggedIn = false;
  }

  public void login(Long id, String username) {
    this.setUsername(username);
    this.setId(id);
  }

  public CurrentUser setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public CurrentUser setEmail(String email) {
    this.email = email;
    return this;
  }

  public CurrentUser setRoles(Set<UserRoleEnum> roles) {
    this.roles = roles;
    return this;
  }

  private void addRole(UserRoleEnum roleEnum) {
    this.roles.add(roleEnum);

  }

  public void saveUserToSession(User loggedInUserEntity) {
    this.setUsername(loggedInUserEntity.getUsername());
    this.setLoggedIn(true);
    this.setFullName(loggedInUserEntity.getFullName());
    this.setId(loggedInUserEntity.getId());
    this.setEmail(loggedInUserEntity.getEmail());

    loggedInUserEntity.getRoles().forEach(r -> this.addRole(r.getRole()));
  }
}
