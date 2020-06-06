package com.maiqu.domain.response;

import lombok.Data;

@Data
public class RoleInfo {
    private Integer id;
    private String roleName;
    private String roleDesc;
    private boolean isSelect;
    private RolePrivilegeInfo rolePrivilegeInfo;
}
