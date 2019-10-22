package com.hi.weiliao.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hi.weiliao.user.mapper.UserFollowMapper;
import com.hi.weiliao.user.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    @Autowired
    private UserFollowMapper userFollowMapper;

    @Override
    public int follow(int userId, int followId) {
        return userFollowMapper.insert(userId, followId);
    }

    @Override
    public int cancelFollow(int userId, int followId) {
        return userFollowMapper.delete(userId, followId);
    }

    @Override
    public Map countFollow(int userId) {
        return userFollowMapper.countFollow(userId);
    }

    @Override
    public boolean exist(int userId, int followId) {
        return userFollowMapper.exist(userId, followId) == 0 ? false : true;
    }

    @Override
    public PageInfo queryFollow(int userId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo(userFollowMapper.queryFollow(userId));
    }

    @Override
    public PageInfo queryFans(int userId, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return new PageInfo(userFollowMapper.queryFans(userId));
    }
}
