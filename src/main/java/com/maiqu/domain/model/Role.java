package com.maiqu.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Role {
    private Integer id;
    private String roleName;
    private String roleDesc;
    private boolean isSelect;
    private List<Privilege> privileges;
}
