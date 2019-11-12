package com.hi.weiliao.user;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.context.BaseContext;
import com.hi.weiliao.base.exception.UserException;
import org.springframework.stereotype.Component;

@Component
public class UserContext extends BaseContext {

    private static final String USER_ID = "USER_ID";

    public static final int NO_LOGIN_ID = 0;

    public void setUserId(Integer userId) {
        set(USER_ID, userId);
    }

    public Integer getUserIdAndCheck() {
        Integer userId = (Integer) get(USER_ID);
        if (null == userId || userId == 0) {
            throw new UserException(ReturnCode.BAD_REQUEST, "尚未登录或过期，请重新登录！");
        }
        return userId;
    }

    public Integer getUserId() {
        Integer userId = (Integer) get(USER_ID);
        if (null == userId) {
            return NO_LOGIN_ID;
        }
        return userId;
    }

    public boolean hasLogin(){
        return getUserId() == NO_LOGIN_ID ? false : true;
    }
}
