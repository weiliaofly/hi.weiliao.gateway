package com.hi.weiliao.user.bean;

public enum UserSourceEnum {

    WX(1, "WX"),
    QQ(2, "QQ"),
    ALI(3, "ALI"),
    UNKNOW(4, "UNKNOW");
    public Integer id;
    public String name;
    UserSourceEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }
}
