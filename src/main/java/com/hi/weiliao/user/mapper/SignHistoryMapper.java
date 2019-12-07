package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.SignHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SignHistoryMapper {

    int insert(SignHistory signHistory);

    SignHistory getById(@Param("userId")Integer userId,
                        @Param("signType")Integer signType,
                        @Param("createTime")String createTime);
}
