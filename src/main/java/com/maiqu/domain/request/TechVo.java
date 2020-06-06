package com.maiqu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TechVo {
    @ApiModelProperty(value = "选课ID")
    private Integer id;
    @ApiModelProperty(value = "课程名称")
    private String className;
    @ApiModelProperty(value = "课时")
    private Double classHour;
    @ApiModelProperty(value = "课程目标")
    private String classObjectives;
    @ApiModelProperty(value = "授课日期 格式：yyyy-MM-dd")
    private String classDate;            //授课日期
    @ApiModelProperty(value = "授课讲师")
    private String  classTearcher;       //授课讲师
    @ApiModelProperty(value = "授课助教")
    private String classAssistants;      //授课助教
    @ApiModelProperty(value = "备注")
    private String note;                 //备注
}
