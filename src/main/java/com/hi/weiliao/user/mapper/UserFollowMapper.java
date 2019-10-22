package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserFollowMapper {

    int insert(@Param("userId") int userId,
               @Param("followId") int followId);

    int delete(@Param("userId") int userId,
               @Param("followId") int followId);

    int exist(@Param("userId") int userId,
              @Param("followId") int followId);

    List<Integer> getByUserId(@Param("userId") int userId);

    List<Integer> getByFollowId(@Param("followId") int followId);

    Map countFollow(@Param("userId") int userId);

    List<UserInfo> queryFollow(@Param("userId") int userId);

    List<UserInfo> queryFans(@Param("userId") int userId);
}
