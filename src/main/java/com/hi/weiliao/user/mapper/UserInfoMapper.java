package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserInfoMapper {

    void insert(UserInfo userInfo);

    UserInfo queryById(@Param("userId")int userId);

    int update(UserInfo userInfo);

    int addCoin(@Param("userId") Integer userId,
                @Param("addCoin") BigDecimal addCoin);
}
