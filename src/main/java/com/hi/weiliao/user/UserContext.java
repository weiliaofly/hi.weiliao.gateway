package com.hi.weiliao.user;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.context.BaseContext;
import com.hi.weiliao.base.exception.UserException;

public class UserContext extends BaseContext {

    private static final String USER_ID = "USER_ID";

    public void setUserId(Integer userId) {
        set(USER_ID, userId);
    }

    public Integer getUserId() {
        Integer userId = (Integer) get(USER_ID);
        if (null == userId || userId == 0) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "获取userid失败");
        }
        return userId;
    }
}
