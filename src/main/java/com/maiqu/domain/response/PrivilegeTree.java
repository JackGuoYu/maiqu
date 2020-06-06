package com.maiqu.domain.response;

import com.maiqu.domain.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class PrivilegeTree {
    private List<Role> roles;
}
