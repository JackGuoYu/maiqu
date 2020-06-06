package com.maiqu.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseSelectVo {
    @ApiModelProperty(value = "选课ID")
    private Integer courseSelectId;
    @ApiModelProperty(value = "课程名称")
    private String className;
    @ApiModelProperty(value = "课时")
    private Double classHour;
    @ApiModelProperty(value = "学校名称")
    private String school;
    @ApiModelProperty(value = "年级")
    private Integer grade;
    @ApiModelProperty(value = "学生人数")
    private Integer studentNumber;
    @ApiModelProperty(value = "授课日期 格式：yyyy-MM-dd HH:mm:ss")
    private String classDate;     //授课日期
    @ApiModelProperty(value = "授课讲师")
    private String  classTearcher;       //授课讲师
    @ApiModelProperty(value = "授课助教")
    private String classAssistants;      //授课助教
    @ApiModelProperty(value = "备注")
    private String note;

}
