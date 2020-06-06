package com.maiqu.util;

import io.swagger.models.auth.In;

public class CommonCode {

    public static final Integer SUCCESS = 200;          //响应成功
    public static final Integer SYSTEM_ERROR = 500;     //系统内部错误
    public static final Integer REQUEST_PARAM_ERROR = 501;  //请求参数错误
    public static final Integer USER_LOCKED = 400;      //账户被锁定
    public static final Integer USER_UNLOGIN = 401;     //用户未登录

    public static final Integer ACTIVE = 1;
    public static final Integer UNACTIVE = 0;

    public static final Integer MAM = 1;    //男
    public static final Integer WOMAN = 0;  //女

    public static final Integer MANAGER = 1;    //管理员
    public static final Integer TEACHER = 2;  //教师
}
