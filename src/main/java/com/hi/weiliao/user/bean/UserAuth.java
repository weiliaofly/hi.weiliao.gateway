package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuth implements Serializable {
    private Integer id;
    private String phone;
    private String passWord;
    private String wxOpenid;
    private String session;
    private String createTime;
    private String reviseTime;
}
