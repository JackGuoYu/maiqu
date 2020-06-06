package com.maiqu.mapper;

import com.maiqu.domain.model.RolePrivilege;
import com.maiqu.domain.request.PrivilegeVo;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePrivilegeMapper {
    Integer bandPrivilege(PrivilegeVo privilegeVo);

    Integer unbandPrivilege(PrivilegeVo privilegeVo);

    List<RolePrivilege> getPrivilegesByRoleId(List<Integer> roleIds);
}
