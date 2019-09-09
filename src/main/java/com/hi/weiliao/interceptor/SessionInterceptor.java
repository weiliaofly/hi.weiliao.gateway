package com.hi.weiliao.interceptor;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private UserContext userContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String session = request.getHeader("Authorization");
        if (StringUtils.isEmpty(session)) {
            throw new UserException(ReturnCode.PARAMETERS_ERROR, "缺少session");
        }
        Integer userId = userAuthService.getUserIdBySession(session);
        if (userId == null || userId == 0) {
            throw new UserException(ReturnCode.BAD_REQUEST, "无效session");
        }
        userContext.setUserId(userId);
        return super.preHandle(request, response, handler);
    }
}
