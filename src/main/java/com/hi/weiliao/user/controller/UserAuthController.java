package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.service.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/userauth")
public class UserAuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ReturnObject query() {
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.query());
    }

    /**
     * 账号密码注册
     * @param register
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ReturnObject register(@RequestBody Map<String, String> register) {
        String phone = register.get("phone");
        String password = register.get("password");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!phone.matches("^1\\d{10}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "手机号错误");
        }
        if (!password.matches("^[A-Za-z0-9\\u4E00-\\u9FA5-]{5,20}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "密码只能由英文，数字，5-20位组成");
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.register(phone, password));
    }

    /**
     * 微信登录，用jscode直接换到openid查询有无存在用户，不存在返回openid
     *
     * @param wxlogin
     * @return
     */
    @RequestMapping(value = "/wxlogin", method = RequestMethod.POST)
    public ReturnObject wxlogin(@RequestBody Map<String, String> wxlogin) {
        String code = wxlogin.get("code");
        if (StringUtils.isEmpty(code)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.wxlogin(code));
    }

    /**
     * 微信授权登录
     *
     * @param wxPhoneLogin
     * @return
     */
    @RequestMapping(value = "/wxPhoneLogin", method = RequestMethod.POST)
    public ReturnObject wxPhoneLogin(@RequestBody Map<String, String> wxPhoneLogin) {
        String encryptedData = wxPhoneLogin.get("encryptedData");
        String iv = wxPhoneLogin.get("iv");
        String openid = wxPhoneLogin.get("openid");
        if (StringUtils.isEmpty(encryptedData) || StringUtils.isEmpty(iv) || StringUtils.isEmpty(openid)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.wxPhoneLogin(openid, encryptedData, iv));
    }

}
