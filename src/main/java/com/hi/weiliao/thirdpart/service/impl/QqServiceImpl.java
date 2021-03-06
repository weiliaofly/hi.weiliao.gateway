package com.hi.weiliao.thirdpart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.config.QqConfig;
import com.hi.weiliao.base.config.WxConfig;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.HttpClientUtil;
import com.hi.weiliao.base.utils.HttpUtils;
import com.hi.weiliao.thirdpart.service.QqService;
import com.hi.weiliao.thirdpart.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class QqServiceImpl implements QqService {

    private final static Object lock = new Object();

    private static String ACCESS_TOKEN = null;

    private static long ACCESS_TOKEN_CREATE_TIME = 0L;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 110 * 60 * 1000L;

    @Autowired
    private QqConfig qqConfig;

    @Override
    public JSONObject code2Session(String jsCode){

        String url = new StringBuilder()
                .append("https://api.q.qq.com/sns/jscode2session?appid=")
                .append(qqConfig.getAppId())
                .append("&secret=")
                .append(qqConfig.getAppSecret())
                .append("&js_code=")
                .append(jsCode)
                .append("&grant_type=authorization_code").toString();

        JSONObject result = HttpUtils.doGet(url, null);
        String openid = result.getString("openid");
        String errcode = result.getString("errcode");
        if (StringUtils.isEmpty(openid)) {
            if ("40029".equals(errcode)) {
                throw new UserException(ReturnCode.BAD_REQUEST, "code无效");
            } else if ("45011".equals(errcode)) {
                throw new UserException(ReturnCode.BAD_REQUEST, "请求频率过高");
            } else if (!"0".equals(errcode)) {
                throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, result.getString("errmsg"));
            }
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "请求微信接口失败");
        }
        return result;
    }

    private String getAccessToken() {

        if (!StringUtils.isEmpty(ACCESS_TOKEN)
                && System.currentTimeMillis() - ACCESS_TOKEN_CREATE_TIME < ACCESS_TOKEN_EXPIRE_TIME) {
            return ACCESS_TOKEN;
        }

        synchronized (lock) {
            String url = new StringBuilder()
                    .append("https://api.q.qq.com/api/getToken?grant_type=client_credential&appid=")
                    .append(qqConfig.getAppId())
                    .append("&secret=")
                    .append(qqConfig.getAppSecret())
                    .toString();
            JSONObject jsonObject = HttpUtils.doGet(url, null);
            String token = jsonObject.getString("access_token");
            String errorcode = jsonObject.getString("errcode");
            if (jsonObject == null){
                throw  new UserException(ReturnCode.INTERNAL_SERVER_DISCONNET,"调用远程接口失败");
            }
            if(StringUtils.isEmpty(token)
                    || (!StringUtils.isEmpty(errorcode) && !"0".equals(errorcode))) {
                throw new UserException(ReturnCode.INTERNAL_SERVER_DISCONNET, "调用远程接口失败："
                        + jsonObject.getString("errcode") + ":" + jsonObject.getString("errmsg"));
            }
            ACCESS_TOKEN_CREATE_TIME = System.currentTimeMillis();
            ACCESS_TOKEN = token;
        }
        return ACCESS_TOKEN;
    }
}
