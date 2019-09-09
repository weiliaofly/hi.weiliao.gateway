package com.hi.weiliao.base;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.base.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleMissingServletRequestParameterException(HttpServletRequest request,
                                                                      MissingServletRequestParameterException ex) {
        StringBuilder msg = new StringBuilder();
        getRequestMessage(request, msg);
        logger.error("参数不符合规则或格式有误" + msg.toString(), ex);
        return new ReturnObject(ReturnCode.PARAMETERS_ERROR.getCode(), "参数 '" + ex.getParameterName() + "'不能为空");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleMissingServletRequestBindingException(HttpServletRequest request,
                                                                    ServletRequestBindingException ex) {
        StringBuilder msg = new StringBuilder();
        getRequestMessage(request, msg);
        if (ex.getMessage().contains("Missing header 'kd-token' for method parameter type")) {
            logger.error(msg.toString());
        } else {
            logger.error("服务器错误" + msg.toString(), ex);
        }
        return new ReturnObject(ReturnCode.PARAMETERS_ERROR.getCode(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleExceptions(HttpServletRequest request, UserException ex) {
        StringBuilder msg = new StringBuilder();
        getRequestMessage(request, msg);
        return new ReturnObject(ex.getReturnCode(), ex.getMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleExceptions(HttpServletRequest request, Exception ex) {
        StringBuilder msg = new StringBuilder();
        getRequestMessage(request, msg);
        return new ReturnObject(ReturnCode.INTERNAL_SERVER_ERROR.getCode(), "服务器错误 ");
    }

    private void getRequestMessage(HttpServletRequest request, StringBuilder msg) {
        msg.append("  请求路径：").append(request.getServletPath());
        msg.append("  请求方式：").append(request.getMethod());
        msg.append("  url拼接参数：").append(request.getQueryString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleMethodArgumentNotValidException(HttpServletRequest request,
                                                              MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        logger.error(errors.get(0).getDefaultMessage());
        return new ReturnObject(ReturnCode.PARAMETERS_ERROR.getCode(), errors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleTypeMismatchException(HttpServletRequest request, Exception ex) {
        StringBuilder msg = new StringBuilder();
        getRequestMessage(request, msg);
        logger.error("服务器错误" + msg.toString(), ex);
        return new ReturnObject(ReturnCode.PARAMETERS_ERROR.getCode(), ex.getLocalizedMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReturnObject handleHttpMessageNotReadableException(HttpServletRequest request,
                                                              HttpMessageNotReadableException ex) {
        logger.error(ex.getMessage(), ex);
        return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "参数格式不正确，请重新检查");
    }


}
