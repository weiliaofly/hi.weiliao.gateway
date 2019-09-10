package com.hi.weiliao.user.service;

import com.github.pagehelper.PageInfo;
import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;

public interface UserManageService {

    PageInfo query(String phone, Integer pageNo, Integer pageSize);

    int deleteByPhone(String phone);
}
