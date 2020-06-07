package com.maiqu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class UserVo {
    @ApiModelProperty(value = "用户ID")
    private Integer userId;
    @ApiModelProperty(value = "用户名称")
    private String userName;
    @ApiModelProperty(value = "用户密码")
    private String passWord;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "出生日期")
    private String  birthDate;
    @ApiModelProperty(value = "age")
    private Integer age;
    @ApiModelProperty(value = "角色ID")
    private Integer  roleId;
    @ApiModelProperty(value = "角色名称")
    private String  roleName;
}
