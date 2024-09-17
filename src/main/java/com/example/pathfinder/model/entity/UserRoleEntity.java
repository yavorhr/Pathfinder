package com.example.pathfinder.model.entity;

import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {
  private UserRoleEnum role;
  private Set<User> users;

  public UserRoleEntity() {
  }

  @ManyToMany(mappedBy = "roles")
  public Set<User> getUsers() {
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

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
