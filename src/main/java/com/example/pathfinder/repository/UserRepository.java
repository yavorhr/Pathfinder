package com.example.pathfinder.repository;

import com.example.pathfinder.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

  void deleteByEmail(String email);

  @Query("SELECT u FROM UserEntity u ORDER BY u.enabled ASC, u.username ASC")
  List<UserEntity> findAllUsersSortedByEnabledFalseAndUsernameAsc();
}
