package com.maiqu.mapper;

import com.maiqu.domain.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    Integer bandRole(Integer userId, Integer roleId);

    Integer unbandRole(Integer userId, Integer roleId);

    Integer deleteRole(Integer userId);

    Integer findUserListCount();

}
