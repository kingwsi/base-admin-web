package com.kingwsi.bs.common.enumerate;

public enum RedisKeyEnum {
    USER_AUTH_INFO("授权用户信息", 10);
    String name;
    Long expire;

    RedisKeyEnum(String name, long expire) {
        this.name = name;
        this.expire = expire;
    }

    public String getName() {
        return this.name;
    }

    public Long getExpire() {
        return this.expire;
    }
}
