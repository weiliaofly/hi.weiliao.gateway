package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.EnumMsgType;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.UserContext;
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

    @Autowired
    private UserContext userContext;

    /**
     * 发送手机验证码
     * @param register
     * @return
     */
    @RequestMapping(value = "/send_vcode", method = RequestMethod.POST)
    public ReturnObject phoneRegister(@RequestBody Map<String, String> register) {
        String phone = register.get("phone");
        String type = register.get("type");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(type)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!phone.matches("^1\\d{10}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "手机号错误");
        }
        if (!type.matches("^\\d{1}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "验证码类型错误");
        }
        EnumMsgType msgType = EnumMsgType.getById(Integer.valueOf(type));
        if(null == msgType){
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "验证码类型不支持");
        }
        userAuthService.sendVCode(phone, msgType);
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 账号注册
     * @param register
     * @return
     */
    @RequestMapping(value = "/register_vcode", method = RequestMethod.POST)
    public ReturnObject vcRegister(@RequestBody Map<String, String> register) {
        String phone = register.get("phone");
        String vCode = register.get("vcode");
        String password = register.get("password");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(vCode) || StringUtils.isEmpty(password)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!phone.matches("^1\\d{10}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "手机号错误");
        }
        if (!vCode.matches("^\\d{6}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "验证码为6位");
        }
        if (!password.matches("^[A-Za-z0-9\\u4E00-\\u9FA5-]{5,20}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "密码只能由英文，数字，5-20位组成");
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.registerByVCode(phone, vCode, password));
    }

    /**
     * 初次登陆设置密码
     * @param register
     * @return
     */
    @RequestMapping(value = "/set_password", method = RequestMethod.POST)
    public ReturnObject setPassword(@RequestBody Map<String, String> register) {
        String password = register.get("password");
        if (StringUtils.isEmpty(password)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!password.matches("^[A-Za-z0-9\\u4E00-\\u9FA5-]{5,20}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "密码只能由英文，数字，5-20位组成");
        }
        userAuthService.setPassword(userContext.getUserId(), password);
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 修改密码
     * @param register
     * @return
     */
    @RequestMapping(value = "/change_password", method = RequestMethod.PUT)
    public ReturnObject changePassword(@RequestBody Map<String, String> register) {
        String oldPassword = register.get("old_password");
        String newPassword = register.get("new_password");
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!newPassword.matches("^[A-Za-z0-9\\u4E00-\\u9FA5-]{5,20}$")
                || !oldPassword.matches("^[A-Za-z0-9\\u4E00-\\u9FA5-]{5,20}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "密码只能由英文，数字，5-20位组成");
        }
        userAuthService.changePassword(userContext.getUserId(), oldPassword, newPassword);
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 短信验证码重置密码（忘记密码）
     * @param register
     * @return
     */
    @RequestMapping(value = "/reset_password", method = RequestMethod.PUT)
    public ReturnObject resetPassword(@RequestBody Map<String, String> register) {
        String phone = register.get("phone");
        String vCode = register.get("vcode");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(vCode)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        if (!phone.matches("^1\\d{10}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "手机号错误");
        }
        if (!vCode.matches("^\\d{6}$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR, "验证码为6位");
        }
        userAuthService.resetPassword(phone, vCode);
        return new ReturnObject(ReturnCode.SUCCESS);
    }

    /**
     * 短信验证码登录
     *
     * @param wxlogin
     * @return
     */
    @RequestMapping(value = "/vcode_login", method = RequestMethod.POST)
    public ReturnObject vcodelogin(@RequestBody Map<String, String> wxlogin) {
        String phone = wxlogin.get("phone");
        String vcode = wxlogin.get("vcode");
        if (StringUtils.isEmpty(vcode) || StringUtils.isEmpty(phone)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.vcodelogin(phone, vcode));
    }

    /**
     * 微信登录，用jscode直接换到openid查询有无存在用户，不存在返回openid
     *
     * @param wxlogin
     * @return
     */
    @RequestMapping(value = "/wx_login", method = RequestMethod.POST)
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
    @RequestMapping(value = "/wx_phone_login", method = RequestMethod.POST)
    public ReturnObject wxPhoneLogin(@RequestBody Map<String, String> wxPhoneLogin) {
        String encryptedData = wxPhoneLogin.get("encrypted_data");
        String iv = wxPhoneLogin.get("iv");
        String openid = wxPhoneLogin.get("openid");
        if (StringUtils.isEmpty(encryptedData) || StringUtils.isEmpty(iv) || StringUtils.isEmpty(openid)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        return new ReturnObject(ReturnCode.SUCCESS, userAuthService.wxPhoneLogin(openid, encryptedData, iv));
    }

}
