package com.maiqu.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class PrivilegeVo {
    private Integer roleId;
    private List<Integer> privilegeIds;
}
