package com.maiqu.mapper;

import com.maiqu.domain.model.Role;
import com.maiqu.domain.model.User;
import com.maiqu.domain.request.dto.PageDto;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper {
    List<Role> getRoles();

    List<Role> getRolesByUserId(Integer userId);

    Integer insertRole(Role role);

    Integer updateRole(Role role);

    Integer deleteRole(Integer roleId);

    Role getRoleByName(String name);

    Role getRoleById(Integer roleId);

    List<Role> findRoleList(PageDto pageDto);

    Integer findRoleListCount();
}
