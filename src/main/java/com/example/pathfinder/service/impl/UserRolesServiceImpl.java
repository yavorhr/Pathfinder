package com.example.pathfinder.service.impl;

import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;
import com.example.pathfinder.repository.UserRoleRepository;
import com.example.pathfinder.service.UserRolesService;
import com.example.pathfinder.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl implements UserRolesService {
  private final UserRoleRepository userRoleRepository;

  public UserRolesServiceImpl(UserRoleRepository userRoleRepository) {
    this.userRoleRepository = userRoleRepository;
  }

  @Override
  public UserRoleEntity findRoleEntityByRoleEnum(UserRoleEnum roleEnum) {
    return this.userRoleRepository.findByRole(roleEnum)
            .orElseThrow(() -> new ObjectNotFoundException("Role with name " + roleEnum + " was not found!"));
  }
}
