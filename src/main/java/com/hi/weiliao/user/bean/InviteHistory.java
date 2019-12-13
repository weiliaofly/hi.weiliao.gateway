package com.hi.weiliao.user.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InviteHistory implements Serializable {
    private Integer userId;
    private Integer inviteId;
    private String createTime;
}
