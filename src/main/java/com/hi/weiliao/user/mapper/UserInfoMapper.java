package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {

    void insert(UserInfo userInfo);

    UserInfo queryById(@Param("userId")int userId);

    int update(UserInfo userInfo);
}
