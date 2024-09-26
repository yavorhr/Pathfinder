package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.LevelEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  private Integer age;
  private String fullName;
  private String email;
  private LevelEnum level;
  private String password;
  private String username;
  private Set<UserRoleEntity> roles;

  public User() {
  }

  @Column
  public Integer getAge() {
    return age;
  }

  @Column(unique = true, nullable = false)
  public String getEmail() {
    return email;
  }

  @Column(name = "full_name", nullable = false)
  public String getFullName() {
    return fullName;
  }

  @Enumerated(EnumType.STRING)
  public LevelEnum getLevel() {
    return level;
  }

  @Column(nullable = false)
  public String getPassword() {
    return password;
  }

  @Column(unique = true,nullable = false)
  public String getUsername() {
    return username;
  }


  @ManyToMany
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  public Set<UserRoleEntity> getRoles() {
    return roles;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public void setLevel(LevelEnum level) {
    this.level = level;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setRoles(Set<UserRoleEntity> roles) {
    this.roles = roles;
  }

  public User setEmail(String email) {
    this.email = email;
    return this;
  }
}
