package com.hi.weiliao.interceptor;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.exception.UserException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManagerInterceptor extends HandlerInterceptorAdapter {

    private static final String M_TOKEN = "d8412!@DG#G5641GED#4F24f$d";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("m-token");
        if (StringUtils.isEmpty(token)) {
            throw new UserException(ReturnCode.PARAMETERS_ERROR, "缺少token");
        }
        if (!M_TOKEN.equals(token)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "token错误");
        }
        return super.preHandle(request, response, handler);
    }
}
