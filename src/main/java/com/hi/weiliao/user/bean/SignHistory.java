package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignHistory implements Serializable {
    private Integer userId;
    private Integer signType;
    private String createTime;
}
