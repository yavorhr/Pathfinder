package com.example.pathfinder.service;

import com.example.pathfinder.model.entity.UserRoleEntity;
import com.example.pathfinder.model.entity.enums.UserRoleEnum;

public interface UserRolesService {

  UserRoleEntity findRoleEntityByRoleEnum(UserRoleEnum roleEnum);
}
