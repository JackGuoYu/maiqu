package com.maiqu.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {
    private Integer id;
    private String userName;
    private String passWord;
    private String phone;
    private Integer sex;
    private Integer roleId;
    private String roleName;
    private String address;
    private Integer age;
    private LocalDateTime birthDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer flag;

}
