package com.hi.weiliao.thirdpart.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.config.WxConfig;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.HttpClientUtil;
import com.hi.weiliao.base.utils.HttpUtils;
import com.hi.weiliao.thirdpart.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {

    private final static Object lock = new Object();

    private static String ACCESS_TOKEN = null;

    private static long ACCESS_TOKEN_CREATE_TIME = 0L;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 110 * 60 * 1000L;

    @Autowired
    private WxConfig wxConfig;

    @Override
    public JSONObject code2Session(String jsCode){

        String url = new StringBuilder()
                .append("https://api.weixin.qq.com/sns/jscode2session?appid=")
                .append(wxConfig.getAppId())
                .append("&secret=")
                .append(wxConfig.getAppSecret())
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

    @Override
    public void msgSecCheck(String content) {

        String url = new StringBuilder()
                .append("https://api.weixin.qq.com/wxa/msg_sec_check?access_token=")
                .append(getAccessToken())
                .toString();

        JSONObject body = new JSONObject();
        body.put("content", content);
        JSONObject jsonObject = HttpClientUtil.httpRequest(url, "POST", body.toJSONString(), null);
        String errorcode = jsonObject.getString("errcode");
        if (jsonObject == null) {
            throw  new UserException(ReturnCode.INTERNAL_SERVER_DISCONNET,"调用远程接口失败");
        }
        if(!StringUtils.isEmpty(errorcode) && !"0".equals(errorcode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "调用远程接口失败"+jsonObject.getString("errcode")+jsonObject.getString("errmsg"));
        }
    }

    private String getAccessToken() {

        if (!StringUtils.isEmpty(ACCESS_TOKEN)
                && System.currentTimeMillis() - ACCESS_TOKEN_CREATE_TIME < ACCESS_TOKEN_EXPIRE_TIME) {
            return ACCESS_TOKEN;
        }

        synchronized (lock) {
            String url = new StringBuilder()
                    .append("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=")
                    .append(wxConfig.getAppId())
                    .append("&secret=")
                    .append(wxConfig.getAppSecret())
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
