package com.hi.weiliao.base.bean;

public enum ReturnCode {

    SUCCESS(200, "成功"),
    NO_CHANGE(201, "成功，无数据更新"),
    NO_CONTENT(204, "无数据"),
    NON_PERMISSION(210, "没有操作权限"),
    OPERATION_FAILED(211, "操作失败"),

    BAD_REQUEST(400, "客户端错误请求"),
    UNAUTHORIZED(401, "用户信息验证失败"),
    PARAMETERS_ERROR(402, "参数不符合规则或格式有误"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    INTERNAL_SERVER_DISCONNET(504, "与服务器连接失败"),
    INSUFFICIENT_STORAGE(507, "服务器无法存储完成请求所必须的内容");

    /**
     * 返回码
     */
    private final int code;

    /**
     * 消息
     */
    private String message;

    ReturnCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

