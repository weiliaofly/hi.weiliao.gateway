package com.hi.weiliao.user.bean;

public enum SignTypeEnum {

    // 日常签到
    SIGN_IN(1, "SIGN_IN");
    public Integer id;
    public String name;
    SignTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
