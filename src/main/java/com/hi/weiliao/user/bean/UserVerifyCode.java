package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVerifyCode {
    private String phone;
    private Integer msgType;
    private String verifyCode;
    private String createTime;
}
