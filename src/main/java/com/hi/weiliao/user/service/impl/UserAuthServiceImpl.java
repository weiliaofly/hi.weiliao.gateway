package com.hi.weiliao.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.config.WxConfig;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.CipherUtils;
import com.hi.weiliao.base.utils.HttpUtils;
import com.hi.weiliao.base.utils.Md5Utils;
import com.hi.weiliao.base.utils.UuidUtils;
import com.hi.weiliao.user.bean.UserAuth;
import com.hi.weiliao.user.mapper.UserAuthMapper;
import com.hi.weiliao.user.service.UserAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private static ConcurrentHashMap<String, String> openidToSessionKey = new ConcurrentHashMap();

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private WxConfig wxConfig;

    @Override
    public String register(String phone, String password) {
        UserAuth userAuth = new UserAuth();
        String session = createSession();
        userAuth.setPhone(phone);
        userAuth.setPassword(Md5Utils.encrypt(password));
        userAuth.setSession(session);
        userAuthMapper.insert(userAuth);
        return session;
    }

    @Override
    public List<UserAuth> query() {
        return userAuthMapper.query();
    }

    @Override
    public UserAuth wxlogin(String jsCode) {

        String url = new StringBuilder()
                .append("https://api.weixin.qq.com/sns/jscode2session?appid=")
                .append(wxConfig.getAppId())
                .append("&secret=")
                .append(wxConfig.getAppSecret())
                .append("&js_code=")
                .append(jsCode)
                .append("&grant_type=authorization_code").toString();

        JSONObject result = HttpUtils.doGet(url, null);
        String errcode = result.getString("errcode");
        if (StringUtils.isEmpty(errcode)) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "请求微信接口失败");
        } else if ("40029".equals(errcode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "code无效");
        } else if ("45011".equals(errcode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "请求频率过高");
        } else if (!"0".equals(errcode)) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, result.getString("errmsg"));
        }

        String openid = result.getString("openid");
        UserAuth userAuth = userAuthMapper.getByOpenid(openid);
        if (userAuth == null) {
            userAuth = new UserAuth();
            userAuth.setWxOpenid(openid);
            openidToSessionKey.put(openid, result.getString("session_key"));
            return userAuth;
        } else {
            return userAuth;
        }
    }

    @Override
    public String wxPhoneLogin(String openid, String encryptedData, String iv) {
        String sessionKey = openidToSessionKey.get(openid);
        if (StringUtils.isEmpty(sessionKey)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "需要先调用微信快捷登录");
        }
        String session = "";
        try {
            String phone = CipherUtils.decryptS5(encryptedData, "UTF-8", sessionKey, iv);
            UserAuth userAuth = new UserAuth();
            session = createSession();
            userAuth.setWxOpenid(openid);
            userAuth.setPhone(phone);
            userAuth.setSession(session);
            userAuthMapper.insert(userAuth);
            openidToSessionKey.remove(openid);
        } catch (Exception e) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "手机号解密出错");
        }
        return session;
    }

    private String createSession() {
        String session = "";
        for (int i = 0; true; i++) {
            session = UuidUtils.getUUID32();
            if (userAuthMapper.getBySession(session) != null) {
                continue;
            }
            if (i == 5) {
                throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "创建session失败");
            }
            break;
        }
        return session;
    }
}
