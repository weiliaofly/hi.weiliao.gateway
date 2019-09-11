package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserManageMapper {

    List<UserAuth> query(@Param("phone")String phone);

    int deleteAuthByPhone(@Param("userId")int userId);

    int deleteInfoByPhone(@Param("userId")int userId);

}
