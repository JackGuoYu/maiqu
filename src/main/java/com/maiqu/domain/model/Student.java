package com.maiqu.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Student {
    private Integer id;
    private String name;
    private Integer age;
    private Integer sex;
    private String school;
    private String guardian;
    private Integer status;
    private BigDecimal tuition; //学费
    private String address;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer flag;
}
