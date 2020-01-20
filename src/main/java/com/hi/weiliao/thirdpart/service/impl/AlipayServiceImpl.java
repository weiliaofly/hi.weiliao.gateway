package com.hi.weiliao.thirdpart.service.impl;

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
import com.hi.weiliao.base.exception.UserException;
import com.hi.weiliao.thirdpart.service.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AliConfig aliConfig;

    @Override
    public boolean sendSms(String phone, EnumMsgType msgType, String verifyCode) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-shenzhen", aliConfig.getAccessKeyId(), aliConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
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
        } catch (ClientException e) {
            throw new UserException(ReturnCode.INTERNAL_SERVER_ERROR, e.getErrMsg());
        }
        return true;
    }
}
