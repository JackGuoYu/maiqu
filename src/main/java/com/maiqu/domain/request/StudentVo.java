package com.maiqu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StudentVo {
    @ApiModelProperty(value = "学员ID")
    private Integer id;
    @ApiModelProperty(value = "学员名称")
    private String name;
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "性别")
    private Integer sex;
    @ApiModelProperty(value = "学校")
    private String school;
    @ApiModelProperty(value = "监护人")
    private String guardian;
    @ApiModelProperty(value = "状态 0-未报名 1-已报名 2-已缴费 3-已毕业")
    private Integer status;
    @ApiModelProperty(value = "学费")
    private BigDecimal  tuition;
    @ApiModelProperty(value = "地址")
    private String  address;
}
