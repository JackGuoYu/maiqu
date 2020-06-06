package com.maiqu.domain.response;

import com.maiqu.domain.model.Privilege;
import lombok.Data;

import java.util.List;

@Data
public class RolePrivilegeInfo {
    private Integer id;
    private String privilegeName;
    private Integer parentId;
    private String path;
    private Integer level;
    private String privilegeDesc;
    private boolean isSelect;
    private List<RolePrivilegeInfo> privileges;
}
