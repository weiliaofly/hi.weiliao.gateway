package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserAuth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserManageMapper {

    List query(@Param("phone")String phone,
               @Param("name")String name,
               @Param("source")String source);

    int deleteAuthByPhone(@Param("userId")int userId);

    int deleteInfoByPhone(@Param("userId")int userId);

}
