package com.hi.weiliao.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hi.weiliao.base.bean.EnumMsgType;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.config.AliConfig;
import com.hi.weiliao.base.config.WxConfig;
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.base.utils.*;
import com.hi.weiliao.user.bean.UserAuth;
import com.hi.weiliao.user.bean.UserVerifyCode;
import com.hi.weiliao.user.mapper.UserAuthMapper;
import com.hi.weiliao.user.mapper.UserVerifyCodeMapper;
import com.hi.weiliao.user.service.UserAuthService;
import com.hi.weiliao.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    private static ConcurrentHashMap<String, String> openidToSessionKey = new ConcurrentHashMap();

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private UserVerifyCodeMapper codeMapper;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private AliConfig aliConfig;

    @Override
    public void sendVCode(String phone, EnumMsgType msgType) {
        if (checkExist(phone)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "该用户已注册");
        }
        String oldCode = codeMapper.queryVaildCodeByPhone(phone, msgType.id);
        if (!StringUtils.isEmpty(oldCode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "验证码未过期，请稍后重试");
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", aliConfig.getAccessKeyId(), aliConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        String verifyCode = CommonUtils.getSixVerifyCode();
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-shenzhen");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "微撩");
        request.putQueryParameter("TemplateCode", msgType.code);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + verifyCode + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            Map result = (Map) JSONObject.parse(response.getData());
            if (!"OK".equals(result.get("Code"))) {
                throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, (String) result.get("Message"));
            }
            UserVerifyCode code = new UserVerifyCode();
            code.setPhone(phone);
            code.setMsgType(msgType.id);
            code.setVerifyCode(verifyCode);
            codeMapper.insert(code);
        } catch (ClientException e) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, e.getErrMsg());
        }
    }

    @Override
    public String registerByVCode(String phone, String vCode, String password) {

        String sendCode = codeMapper.queryVaildCodeByPhone(phone, EnumMsgType.REGISTER.id);
        if (StringUtils.isEmpty(sendCode) || !sendCode.equals(vCode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "验证码过期或无效");
        }
        return register(phone, "", password);
    }

    @Override
    public void setPassword(Integer userId, String password) {
        UserAuth exist = userAuthMapper.getById(userId);
        if (exist == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        if (!StringUtils.isEmpty(exist.getPassword())) {
            throw new UserException(ReturnCode.BAD_REQUEST, "需要先短信验证才能重置密码");
        }
        UserAuth updateUA = new UserAuth();
        updateUA.setId(userId);
        updateUA.setPassword(Md5Utils.encrypt(password));
        userAuthMapper.update(updateUA);
    }

    @Override
    public void changePassword(Integer userId, String oldPassword, String newPassword) {
        UserAuth exist = userAuthMapper.getById(userId);
        if (exist == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        oldPassword = Md5Utils.encrypt(oldPassword);
        newPassword = Md5Utils.encrypt(newPassword);
        if (!oldPassword.equals(exist.getPassword())) {
            throw new UserException(ReturnCode.BAD_REQUEST, "密码错误");
        }
        UserAuth updateUA = new UserAuth();
        updateUA.setId(userId);
        updateUA.setPassword(newPassword);
        userAuthMapper.update(updateUA);
    }

    @Override
    public void resetPassword(String phone, String vCode) {

        UserAuth exist = userAuthMapper.getByPhone(phone);
        if (exist == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        String sendCode = codeMapper.queryVaildCodeByPhone(phone, EnumMsgType.RESET_PASSWORD.id);
        if (StringUtils.isEmpty(sendCode) || !sendCode.equals(vCode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "验证码过期或无效");
        }
        UserAuth updateUA = new UserAuth();
        updateUA.setId(exist.getId());
        updateUA.setPassword("");
        userAuthMapper.update(updateUA);
    }


    @Override
    public String passwordlogin(String phone, String password) {
        UserAuth exist = userAuthMapper.getByPhone(phone);
        if (exist == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        if (exist.getPwTryTimes() >= 3) {
            throw new UserException(ReturnCode.BAD_REQUEST, "密码已被锁定，请使用短信验证登录");
        }
        String existPW = exist.getPassword();
        if (StringUtils.isEmpty(existPW)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "尚未设置密码，请用短信验证登录");
        }
        password = Md5Utils.encrypt(password);
        if (!existPW.equals(password)) {
            UserAuth updateUA = new UserAuth();
            updateUA.setId(exist.getId());
            updateUA.setPwTryTimes(updateUA.getPwTryTimes() + 1);
            userAuthMapper.update(updateUA);
            throw new UserException(ReturnCode.BAD_REQUEST, "密码错误");
        }
        String session = createSession();
        UserAuth updateUA = new UserAuth();
        updateUA.setId(exist.getId());
        updateUA.setSession(session);
        userAuthMapper.update(updateUA);
        return session;
    }

    @Override
    public String vcodelogin(String phone, String vCode) {
        UserAuth exist = userAuthMapper.getByPhone(phone);
        if (exist == null) {
            throw new UserException(ReturnCode.BAD_REQUEST, "用户不存在");
        }
        String sendCode = codeMapper.queryVaildCodeByPhone(phone, EnumMsgType.LOGIN.id);
        if (StringUtils.isEmpty(sendCode) || !sendCode.equals(vCode)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "验证码过期或无效");
        }
        String session = createSession();
        UserAuth updateUA = new UserAuth();
        updateUA.setId(exist.getId());
        updateUA.setPwTryTimes(0);
        updateUA.setSession(session);
        userAuthMapper.update(updateUA);
        return session;
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

        String phone = "";
        try {
            phone = CipherUtils.decryptS5(encryptedData, "UTF-8", sessionKey, iv);
        } catch (Exception e) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, "手机号解密出错");
        }
        String session = register(phone, openid, "");
        openidToSessionKey.remove(openid);
        return session;
    }

    @Override
    public Integer getUserIdBySession(String session) {
        UserAuth userAuth = userAuthMapper.getBySession(session);
        if (userAuth != null) {
            return userAuth.getId();
        }
        return 0;
    }

    private String register(String phone, String wxOpenId, String password) {
        if (checkExist(phone)) {
            throw new UserException(ReturnCode.BAD_REQUEST, "该用户已注册");
        }
        String session = "";
        UserAuth userAuth = new UserAuth();
        session = createSession();
        userAuth.setWxOpenid(wxOpenId);
        userAuth.setPhone(phone);
        if (!StringUtils.isEmpty(password)) {
            userAuth.setPassword(Md5Utils.encrypt(password));
        }
        userAuth.setSession(session);
        userAuthMapper.insert(userAuth);
        userInfoService.initUserInfo(userAuth.getId(), phone);
        return session;
    }

    private boolean checkExist(String phone){
        UserAuth exUserAuth = userAuthMapper.getByPhone(phone);
        if (exUserAuth != null) {
            return true;
        }
        return false;
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
