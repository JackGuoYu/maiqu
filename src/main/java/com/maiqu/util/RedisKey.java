package com.maiqu.util;

public class RedisKey {
    //用户登录重试次数
    public static final String LOGIN_RETYY_NUMBER_KEY = "LOGIN_RETYY_NUMBER";

    //用户登录锁定
    public static final String LOGIN_LOCK_KEY = "LOGIN_LOCK_KEY";

    //用户token缓存key
    public static final String USER_TOKEN_KEY = "USER_TOKEN_KEY";

    //用户token映射的用户信息
    public static final String TOKEN_GET_USER_KEY = "TOKEN_GET_USER_KEY";
}
