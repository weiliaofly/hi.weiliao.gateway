package com.hi.weiliao.base.exception;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private ReturnCode returnCode;

    private String msg;

    public UserException(ReturnCode returnCode) {
        super(returnCode.getMessage());
        this.returnCode = returnCode;
    }

    public UserException(String urlStr, ReturnCode returnCode) {
        super(returnCode.getMessage());
        this.returnCode = returnCode;
    }

    public UserException(ReturnCode returnCode, String msg) {
        super(msg);
        this.msg = msg;
        this.returnCode = returnCode;
    }

    public ReturnCode getReturnCode() {
        return returnCode;
    }

    public String getMsg() {
        return msg;
    }

    public ReturnObject getReturnObject() {
        return new ReturnObject(getReturnCode() == null ? ReturnCode.PARAMETERS_ERROR : getReturnCode(), this.getMessage());
    }

}
