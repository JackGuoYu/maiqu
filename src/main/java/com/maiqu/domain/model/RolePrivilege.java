package com.maiqu.domain.model;

import lombok.Data;

@Data
public class RolePrivilege {
    Integer id;
    Integer roleId;
    Integer privilegeId;
}
