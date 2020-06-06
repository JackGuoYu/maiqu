package com.maiqu.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Privilege {
    private Integer id;
    private String privilegeName;
    private Integer parentId;
    private String path;
    private Integer level;
    private String privilegeDesc;
    private boolean isSelect;
    private List<Privilege> privileges;
}
