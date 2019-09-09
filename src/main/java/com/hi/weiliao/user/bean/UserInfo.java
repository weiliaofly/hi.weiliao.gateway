package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {
    private Integer userId;
    private String name;
    private String headIcon;
    private String background;
    private String personalSign;
    private Integer sex;
    private String birthday;
    private String address;
    private String reviseTime;
}
