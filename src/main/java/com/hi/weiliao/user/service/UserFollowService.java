package com.hi.weiliao.user.service;

public interface UserFollowService {

    void follow(int userId, int followId);

    void cancelFollow(int userId, int followId);
}
