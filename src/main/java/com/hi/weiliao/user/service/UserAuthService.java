package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;


public interface UserAuthService {

    String register(String phone, String password);

    List<UserAuth> query();

    UserAuth wxlogin(String jscode);

    String wxPhoneLogin(String openid, String encryptedData, String iv);
}
