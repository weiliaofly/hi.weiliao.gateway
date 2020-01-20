package com.hi.weiliao.thirdpart.service;

import com.alibaba.fastjson.JSONObject;

public interface WechatService {

    JSONObject code2Session(String jsCode);

    void msgSecCheck(String content);
}
