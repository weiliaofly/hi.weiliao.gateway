package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.mapper.UserInfoMapper;
import com.hi.weiliao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void initUserInfo(int userId, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setName(phone);
        userInfoMapper.insert(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(int userId) {
        return userInfoMapper.queryById(userId);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return userInfoMapper.update(userInfo);
    }
}
