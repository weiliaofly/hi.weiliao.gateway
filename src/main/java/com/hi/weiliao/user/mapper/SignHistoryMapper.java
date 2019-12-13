package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.SignHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignHistoryMapper {

    int insert(SignHistory signHistory);

    SignHistory getById(@Param("userId")Integer userId,
                        @Param("signType")Integer signType,
                        @Param("createTime")String createTime);

    List<SignHistory> getSignHistory(@Param("userId")Integer userId,
                                     @Param("fromOn")String fromOn,
                                     @Param("toOn")String toOn);
}
