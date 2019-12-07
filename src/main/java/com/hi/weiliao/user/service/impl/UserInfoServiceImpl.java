package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.mapper.UserInfoMapper;
import com.hi.weiliao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public void initUserInfo(int userId, String name) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        if (StringUtils.isEmpty(name)) {
            userInfo.setName(UUID.randomUUID().toString().substring(0, 20));
        } else {
            userInfo.setName(name);
        }
        userInfoMapper.insert(userInfo);
    }

    @Override
    public void insertUserInfo(UserInfo userInfo) {
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

    @Override
    public int addCoin(int userId, BigDecimal addCoin) {
        return userInfoMapper.addCoin(userId, addCoin);
    }
}
