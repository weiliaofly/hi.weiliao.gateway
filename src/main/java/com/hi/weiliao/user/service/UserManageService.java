package com.hi.weiliao.user.service;

import com.github.pagehelper.PageInfo;
import com.hi.weiliao.user.bean.UserAuth;

import java.util.List;

public interface UserManageService {

    PageInfo query(String phone, String name, String source,
                   Integer pageNo, Integer pageSize, String orderBy);

    int deleteByPhone(String phone);

    int deleteByUserId(Integer userId);
}
