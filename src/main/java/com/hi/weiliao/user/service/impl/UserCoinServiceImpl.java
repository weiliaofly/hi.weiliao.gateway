package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.CommonUtils;
import com.hi.weiliao.user.bean.UserInfo;
import com.hi.weiliao.user.mapper.UserInfoMapper;
import com.hi.weiliao.user.service.UserCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserCoinServiceImpl implements UserCoinService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public boolean subCoin(int userId, BigDecimal coin) {
        UserInfo userInfo = userInfoMapper.queryById(userId);
        if (coin == null || CommonUtils.isBigger(BigDecimal.ZERO, coin)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "金额错误：" + coin);
        }
        if (userInfo == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        if (!CommonUtils.isBigger(userInfo.getCoin(), coin)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "余额不足");
        }
        userInfoMapper.addCoin(userId, coin.negate());
        return true;
    }
}
