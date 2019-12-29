package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.InviteHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteHistoryMapper {

    int insert(InviteHistory inviteHistory);

    List<InviteHistory> getById(@Param("userId") Integer userId,
                                @Param("inviteId") Integer inviteId);
}
