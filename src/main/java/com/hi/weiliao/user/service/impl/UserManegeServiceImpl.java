package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.user.bean.UserAuth;
import com.hi.weiliao.user.mapper.UserManageMapper;
import com.hi.weiliao.user.service.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManegeServiceImpl implements UserManageService {

    @Autowired
    private UserManageMapper userManageMapper;

    @Override
    public List<UserAuth> query() {
        return userManageMapper.query();
    }

    @Override
    public int deleteByPhone(String phone){
        return userManageMapper.deleteByPhone(phone);
    }
}
