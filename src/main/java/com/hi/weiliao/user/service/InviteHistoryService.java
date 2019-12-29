package com.hi.weiliao.user.service;

import com.hi.weiliao.user.bean.InviteHistory;

import java.util.List;

public interface InviteHistoryService {

    int invite(Integer userId, Integer inviteId);

    List<InviteHistory> getInviteHistory(Integer userId, Integer inviteId);
}
