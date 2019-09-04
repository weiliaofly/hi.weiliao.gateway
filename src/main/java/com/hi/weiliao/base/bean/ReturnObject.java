package com.hi.weiliao.base.bean;

public class ReturnObject {
    private int code;
    private String msg;
    private Object data;

    public ReturnObject() {
    }

    public ReturnObject(ReturnCode rc, Object data) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
        this.data = data;
    }

    public ReturnObject(ReturnCode rc, String msg, Object data) {
        this.code = rc.getCode();
        this.msg = msg;
        this.data = data;
    }

    public ReturnObject(ReturnCode rc, String data) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
        this.data = data;
    }

    public ReturnObject(ReturnCode rc) {
        this.code = rc.getCode();
        this.msg = rc.getMessage();
    }

    public ReturnObject(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
