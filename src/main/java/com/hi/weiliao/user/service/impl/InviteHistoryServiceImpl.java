package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.TimeUtils;
import com.hi.weiliao.user.bean.InviteHistory;
import com.hi.weiliao.user.mapper.InviteHistoryMapper;
import com.hi.weiliao.user.service.InviteHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InviteHistoryServiceImpl implements InviteHistoryService {

    @Autowired
    private InviteHistoryMapper inviteHistoryMapper;

    @Override
    public int invite(Integer userId, Integer inviteId) {
        if (userId == null || inviteId == null) {
            return 0;
        }
        InviteHistory inviteHistory = new InviteHistory();
        inviteHistory.setUserId(userId);
        inviteHistory.setInviteId(inviteId);
        inviteHistory.setCreateTime(TimeUtils.getCurrentYYYYMMDDHHMMSS());
        return inviteHistoryMapper.insert(inviteHistory);
    }

    @Override
    public List<InviteHistory> getInviteHistory(Integer userId, Integer inviteId) {
        if (userId == null && inviteId == null) {
            return null;
        }
        return inviteHistoryMapper.getById(userId, inviteId);
    }
}
