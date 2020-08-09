package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuth implements Serializable {
    private Integer id;
    private String phone;
    private String password;
    private Integer pwTryTimes;
    private String wxOpenid;
    private String qqOpenid;
    private String source;
    private String session;
    private String createTime;
    private String reviseTime;
}
