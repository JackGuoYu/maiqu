package com.maiqu.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TechInfo {
    private Integer id;
    private String className;
    private Double classHour;
    private String classObjectives;
    private LocalDateTime classDate;     //授课日期
    private String  classTearcher;       //授课讲师
    private String classAssistants;      //授课助教
    private String note;                 //备注
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer flag;
}
