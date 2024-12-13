package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
  private UserRoleEnum role;
  private Set<UserEntity> users;

  public UserRoleEntity() {
  }

  public UserRoleEntity(UserRoleEnum role) {
    this.role = role;
  }

  @ManyToMany(mappedBy = "roles")
  public Set<UserEntity> getUsers() {
    return users;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  public UserRoleEnum getRole() {
    return role;
  }

  public void setRole(UserRoleEnum role) {
    this.role = role;
  }

  public void setUsers(Set<UserEntity> users) {
    this.users = users;
  }
}
