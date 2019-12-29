package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InviteHistory implements Serializable {
    private Integer userId;
    private Integer inviteId;
    private BigDecimal addCoin;
    private String createTime;
}
