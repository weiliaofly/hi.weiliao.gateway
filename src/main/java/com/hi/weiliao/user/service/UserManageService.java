package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;

public interface UserManageService {

    List<UserAuth> query();

    int deleteByPhone(String phone);
}
