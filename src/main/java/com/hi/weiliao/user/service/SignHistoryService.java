package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.SignHistory;

import java.util.List;

public interface SignHistoryService {

    boolean signIn(Integer userId);

    List<SignHistory> getSignHistory(Integer userId, String fromOn, String toOn);
}