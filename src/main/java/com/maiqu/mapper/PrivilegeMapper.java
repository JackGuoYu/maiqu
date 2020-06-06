package com.maiqu.mapper;

import com.maiqu.domain.model.Privilege;
import com.maiqu.domain.model.Role;
import com.maiqu.domain.request.dto.PageDto;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeMapper {

    List<Privilege> getPrivilegeByRoleId(Integer roleId);

    List<Privilege> getPrivilegeList();

    Privilege getPrivilegeByName(String name);

    Privilege getPrivilegeById(Integer id);

    Integer insertPrivilege(Privilege privilege);

    Integer updatePrivilege(Privilege privilege);

    Integer deletePrivilege(Integer privilegeId);

    List<Privilege> findPrivilegeList(PageDto pageDto);

    Integer findPrivilegeListCount();
}
