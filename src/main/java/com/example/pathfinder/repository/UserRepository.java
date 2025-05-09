package com.example.pathfinder.repository;

import com.example.pathfinder.model.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findByEmail(String email);

  void deleteByEmail(String email);

  @Query("SELECT u FROM UserEntity u ORDER BY u.enabled ASC, u.username ASC")
  List<UserEntity> findAllUsersSortedByEnabledFalseAndUsernameAsc();

  @Query("SELECT u FROM UserEntity u Where u.accountLocked")
  List<UserEntity> findAllLockedUsers();

  @Query("SELECT u FROM UserEntity u WHERE u.lastLoginTime IS NOT NULL AND u.lastLoginTime < :oneYearAgo AND u.accountExpired = false")
  List<UserEntity> findInactiveUsersSinceOneYear(LocalDateTime oneYearAgo);

  Page<UserEntity> findByEmailContainingIgnoreCase(String email, Pageable pageable);

}
