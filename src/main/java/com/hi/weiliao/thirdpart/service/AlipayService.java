package com.hi.weiliao.thirdpart.service;

import com.hi.weiliao.base.bean.EnumMsgType;

public interface AlipayService {

    boolean sendSms(String phone, EnumMsgType msgType, String verifyCode);
}
