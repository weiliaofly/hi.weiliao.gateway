package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.user.mapper.UserFollowMapper;
import com.hi.weiliao.user.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public void follow(int userId, int followId) {
        userFollowMapper.insert(userId, followId);
    }

    @Override
    public void cancelFollow(int userId, int followId) {
        userFollowMapper.delete(userId, followId);
    }
}
