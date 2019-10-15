package com.hi.weiliao.user.controller;

import com.hi.weiliao.base.BaseController;
import com.hi.weiliao.base.bean.ReturnCode;
import com.hi.weiliao.base.bean.ReturnObject;
import com.hi.weiliao.user.UserContext;
import com.hi.weiliao.user.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/userfollow")
public class UserFollowController extends BaseController {

    @Autowired
    private UserFollowService userFollowService;


    @Autowired
    private UserContext userContext;

    /**
     * 发送手机验证码
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/follow", method = RequestMethod.POST)
    public ReturnObject follow(@RequestBody Map<String, String> params) {
        String followId = params.get("follow_id");
        if (StringUtils.isEmpty(followId) || !followId.matches("^\\d$")) {
            return new ReturnObject(ReturnCode.PARAMETERS_ERROR);
        }
        userFollowService.follow(userContext.getUserId(), followId);
    }
}
