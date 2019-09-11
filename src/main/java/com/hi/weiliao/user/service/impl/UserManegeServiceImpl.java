package com.hi.weiliao.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.user.bean.UserAuth;
import com.hi.weiliao.user.mapper.UserAuthMapper;
import com.hi.weiliao.user.mapper.UserManageMapper;
import com.hi.weiliao.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManegeServiceImpl implements UserManageService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public PageInfo query(String phone, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo(userManageMapper.query(phone));
    }

    @Override
    public int deleteByPhone(String phone) {
        UserAuth userAuth = userAuthMapper.getByPhone(phone);
        if (userAuth == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        userManageMapper.deleteInfoByPhone(userAuth.getId());
        return userManageMapper.deleteAuthByPhone(userAuth.getId());
    }
}
