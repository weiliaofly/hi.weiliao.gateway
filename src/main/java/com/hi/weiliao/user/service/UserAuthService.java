package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;


public interface UserAuthService {

    void sendRegisterVCode(String phone);

    String registerByVCode(String phone, String vCode);

    List<UserAuth> query();

    UserAuth wxlogin(String jscode);

    String wxPhoneLogin(String openid, String encryptedData, String iv);

    Integer getUserIdBySession(String session);
}
