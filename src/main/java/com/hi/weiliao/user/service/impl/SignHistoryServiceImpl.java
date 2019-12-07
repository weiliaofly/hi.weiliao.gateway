package com.hi.weiliao.user.service.impl;

import com.hi.weiliao.user.bean.SignHistory;
import com.hi.weiliao.user.bean.SignTypeEnum;
import com.hi.weiliao.user.mapper.SignHistoryMapper;
import com.hi.weiliao.user.service.SignHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SignHistoryServiceImpl implements SignHistoryService {

    @Autowired
    private SignHistoryMapper signHistoryMapper;

    @Override
    public boolean signIn(Integer userId) {
        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd");
        String currentDate = sf.format(new Date());
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
}
