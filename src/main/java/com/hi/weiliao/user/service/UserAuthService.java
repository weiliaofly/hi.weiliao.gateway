package com.hi.weiliao.user.service;

import com.hi.weiliao.base.bean.EnumMsgType;
import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;


public interface UserAuthService {

    void sendVCode(String phone, EnumMsgType msgType);

    String registerByVCode(String phone, String vCode, String password);

    void setPassword(Integer userId, String password);

    void changePassword(Integer userId, String oldPassword, String newPassword);

    void resetPassword(String phone, String vCode);

    String passwordlogin(String phone, String password);

    String vcodelogin(String phone, String vCode);

    UserAuth wxlogin(String jscode);

    String wxPhoneLogin(String openid, String encryptedData, String iv);

    Integer getUserIdBySession(String session);
}
