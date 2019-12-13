package com.hi.weiliao.user.service;

import com.hi.weiliao.base.bean.EnumMsgType;
import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.UserAuth;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserAuthService {

    void sendVCode(Integer userId, String phone, EnumMsgType msgType);

    String registerByVCode(String phone, String vCode, String password, Integer inviteId);

    void setPassword(Integer userId, String password);

    void changePassword(Integer userId, String oldPassword, String newPassword);

    void resetPassword(String phone, String vCode);

    void setPhone(Integer userId, String phone, String vCode);

    String passwordlogin(String phone, String password);

    String vcodelogin(String phone, String vCode);

    UserAuth wxlogin(String jscode);

    String wxPhoneLogin(String openid, String encryptedData, String iv, Integer inviteId);

    String wxInfoLogin(String openid, String encryptedData, String iv, Integer inviteId);

    Integer getUserIdBySession(String session);

    UserAuth getExistById (int userId);

    void signIn(Integer userId);

    List<SignHistory> getSignHistory(Integer userId, String fromOn, String toOn);
}
