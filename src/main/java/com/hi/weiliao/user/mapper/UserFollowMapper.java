package com.hi.weiliao.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowMapper {

    int insert(@Param("userId") int userId,
               @Param("followId") int followId);

    int delete(@Param("userId") int userId,
               @Param("followId") int followId);

    List<Integer> getByUserId(@Param("userId") int userId);

    List<Integer> getByFollowId(@Param("followId") int followId);
}
