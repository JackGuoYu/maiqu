package com.maiqu.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CourseSelectInfo {

    private Integer id;
    private String className;
    private Double classHour;
    private String school;
    private Integer grade;
    private Integer studentNumber;
    private LocalDateTime classDate;     //授课日期
    private String  classTearcher;       //授课讲师
    private String classAssistants;      //授课助教
    private String note;                 //备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer flag;
}
