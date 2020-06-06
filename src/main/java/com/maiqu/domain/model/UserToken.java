package com.maiqu.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserToken {
    private Integer id;
    private Integer userId;
    private String token;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Integer flag;

}
