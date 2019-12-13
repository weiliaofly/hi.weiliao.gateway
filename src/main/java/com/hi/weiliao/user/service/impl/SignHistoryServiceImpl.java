package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.base.utils.TimeUtils;
import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.SignTypeEnum;
import com.hi.weiliao.user.mapper.SignHistoryMapper;
import com.hi.weiliao.user.service.SignHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignHistoryServiceImpl implements SignHistoryService {

    @Autowired
    private SignHistoryMapper signHistoryMapper;

    @Override
    public boolean signIn(Integer userId) {
        String currentDate = TimeUtils.getCurrentYYYYMMDDHHMMSS();
        SignHistory signHistory = signHistoryMapper.getById(userId, SignTypeEnum.SIGN_IN.id, currentDate);
        if (signHistory != null) {
            return false;
        }
        SignHistory history = new SignHistory();
        history.setUserId(userId);
        history.setSignType(SignTypeEnum.SIGN_IN.id);
        history.setCreateTime(currentDate);
        signHistoryMapper.insert(history);
        return true;
    }

    @Override
    public List<SignHistory> getSignHistory(Integer userId, String fromOn, String toOn) {
        return signHistoryMapper.getSignHistory(userId, fromOn, toOn);
    }
}
