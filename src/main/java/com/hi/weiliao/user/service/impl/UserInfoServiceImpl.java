package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.service.GlobalConfigService;
import com.hi.weiliao.user.bean.CoinConfigEnum;
import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.mapper.UserInfoMapper;
import com.hi.weiliao.user.service.SignHistoryService;
import com.hi.weiliao.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private GlobalConfigService globalConfigService;

    @Autowired
    private SignHistoryService signHistoryService;

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

    @Override
    public void signIn(Integer userId) {
        boolean success = signHistoryService.signIn(userId);
        if (!success) {
            throw new UserException(ReturnCode.BAD_REQUEST, "今天已签过到");
        }
        String signInCoin = globalConfigService.getConfigValue(CoinConfigEnum.SIGN_IN.configKey);
        BigDecimal addCoin = new BigDecimal(signInCoin);
        addCoin(userId, addCoin);
    }

    @Override
    public List<SignHistory> getSignHistory(Integer userId, String fromOn, String toOn) {
        return signHistoryService.getSignHistory(userId, fromOn, toOn);
    }
}
