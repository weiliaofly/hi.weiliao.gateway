package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.UserInfo;

import java.math.BigDecimal;
import java.util.List;

public interface UserInfoService {

    void initUserInfo(int userId, String name);

    void insertUserInfo(UserInfo userInfo);

    UserInfo getUserInfoById(int userId);

    int updateUserInfo(UserInfo userInfo);

    int addCoin(int userId, BigDecimal addCoin);

    void signIn(Integer userId);

    List<SignHistory> getSignHistory(Integer userId, String fromOn, String toOn);
}
