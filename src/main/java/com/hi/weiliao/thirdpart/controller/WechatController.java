package com.hi.weiliao.thirdpart.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.thirdpart.service.WechatService;
import com.hi.weiliao.user.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/wechat")
public class WechatController extends BaseController {

    @Autowired
    private UserContext userContext;

    @Autowired
    private WechatService wechatService;

    /**
     * 微信审核
     * @param body
     * @return
     */
    @RequestMapping(value = "/msg_sec_check", method = RequestMethod.POST)
    public ReturnObject msgSecCheck(@RequestBody Map<String, String> body) {
        userContext.getUserIdAndCheck();
        String content = body.get("content");
        if (StringUtils.isEmpty(content)) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        wechatService.msgSecCheck(content);
        return new ReturnObject(ReturnCode.SUCCESS);
    }
}
