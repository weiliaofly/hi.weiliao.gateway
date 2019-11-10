package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.UserInfo;

public interface UserInfoService {

    void initUserInfo(int userId, String name);

    void insertUserInfo(UserInfo userInfo);

    UserInfo getUserInfoById(int userId);

    int updateUserInfo(UserInfo userInfo);
}
