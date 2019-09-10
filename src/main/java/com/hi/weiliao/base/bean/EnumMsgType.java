package com.hi.weiliao.base.bean;

public enum EnumMsgType {

    // 1-注册
    REGISTER("注册", "SMS_173760507", 1),

    // 2-登录
    LOGIN("登录", "SMS_173762205", 2),

    // 3-重置密码
    RESET_PASSWORD("重置密码", "SMS_173767011", 3);

    public String name;
    public String code;
    public Integer id;

    EnumMsgType(String name, String code, Integer id) {
        this.name = name;
        this.code = code;
        this.id = id;
    }

    public static EnumMsgType getById(int id) {
        for (EnumMsgType msgType : EnumMsgType.values()) {
            if (msgType.id == id) {
                return msgType;
            }
        }
        return null;
    }
}
