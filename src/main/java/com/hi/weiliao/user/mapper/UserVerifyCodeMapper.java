package com.hi.weiliao.user.mapper;

import com.hi.weiliao.user.bean.UserVerifyCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerifyCodeMapper {

    int insert(UserVerifyCode verifyCode);

    String queryVaildCodeByPhone(@Param("phone") String phone,
                                 @Param("msgType") Integer msgType);
}
