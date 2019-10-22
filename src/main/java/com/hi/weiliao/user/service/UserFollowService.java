package com.hi.weiliao.user.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface UserFollowService {

    int follow(int userId, int followId);

    int cancelFollow(int userId, int followId);

    boolean exist(int userId, int followId);

    Map countFollow(int userId);

    PageInfo queryFollow(int userId, int pageNo, int pageSize);

    PageInfo queryFans(int userId, int pageNo, int pageSize);
}
