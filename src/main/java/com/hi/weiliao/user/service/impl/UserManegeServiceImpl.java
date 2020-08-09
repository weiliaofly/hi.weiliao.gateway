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
    public PageInfo query(String phone, String name, String source,
                          Integer pageNo, Integer pageSize, String orderBy) {
        PageHelper.startPage(pageNo, pageSize, orderBy);
        return new PageInfo(userManageMapper.query(phone, name, source));
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

    @Override
    public int deleteByUserId(Integer userId) {
        userManageMapper.deleteInfoByPhone(userId);
        return userManageMapper.deleteAuthByPhone(userId);
    }
}
