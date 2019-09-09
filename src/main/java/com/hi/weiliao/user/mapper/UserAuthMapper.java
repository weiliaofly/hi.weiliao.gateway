package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAuthMapper {

    int insert(UserAuth userAuth);

    int update(UserAuth userAuth);

    UserAuth getByOpenid(@Param("wxOpenid") String wxOpenid);

    UserAuth getBySession(@Param("session") String session);

    UserAuth getByPhone(@Param("phone") String phone);

    List<UserAuth> query();
}
