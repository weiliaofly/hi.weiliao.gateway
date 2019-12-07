package com.hi.weiliao.user.bean;

public enum CoinConfigEnum {

    SIGN_IN("签到赠送", "SIGN_IN"),

    INVITE("邀请赠送", "INVITE");

    public String name;
    public String configKey;

    CoinConfigEnum(String name, String configKey) {
        this.name = name;
        this.configKey = configKey;
    }
}
